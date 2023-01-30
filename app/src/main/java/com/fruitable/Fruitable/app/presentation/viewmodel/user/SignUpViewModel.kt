package com.fruitable.Fruitable.app.presentation.viewmodel.user

import android.util.Patterns
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.*
import com.fruitable.Fruitable.app.presentation.event.SignUpEvent
import com.fruitable.Fruitable.app.presentation.state.TextFieldBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val userUseCase: UserUseCase
) : ViewModel(){

    private val _nickname = mutableStateOf(
        TextFieldBoxState(
        title = "닉네임",
        hint = "8자 이하의 한글, 영어, 숫자만 입력해주세요.",
        error = "적절하지 않은 닉네임입니다. 다시 입력해주세요."
    )
    )
    val nickname: State<TextFieldBoxState> = _nickname

    private val _email = mutableStateOf(
        TextFieldBoxState(
            title = "이메일",
            error = "올바른 이메일을 입력해주세요."
        )
    )
    val email: State<TextFieldBoxState> = _email

    private val _emailCode = mutableStateOf(TextFieldBoxState(error = "정확한 인증번호 6자리를 입력해주세요."))
    val emailCode: State<TextFieldBoxState> = _emailCode

    private val _password = mutableStateOf(
        TextFieldBoxState(
            title = "비밀번호",
            hint = "8자 이상의 비밀번호를 설정해주세요.",
            error = "적절하지 않은 비밀번호입니다.  다시 입력해주세요."
        )
    )
    val password = _password

    private val _password2 = mutableStateOf(
        TextFieldBoxState(
            title = "비밀번호 확인",
            error = "비밀번호가 일치하지 않습니다. 다시 입력해주세요."
        )
    )
    val password2 = _password2
    /**
     * [인증번호 state 단계]
     *      0. EMAIL_INPUT 초기 단 ("email")
     *      1. EMAIL_INPUT_SUCCESS 이메일 입력 ("email success") | example@naver.com 의 형식이 아닌 경우
     *      2. EMAIL_SEND_CERTIFICATION 인증번호 발송, 재발송 ("send emailCode")
     *      3. EMAIL_CERTIFICATION_INPUT 인증번호 입력  ("certification success") | 숫자로만 구성된게 아닐 경우, 시간 초과
     *      4. EMAIL_CERTIFICATION_INPUT_SUCCESS -> 수정 불가
     */
    val certification = mutableStateOf(EMAIL_INPUT)
    private val nicknameValid = mutableStateOf(false)
    private val emailValid = mutableStateOf(false)
    private val emailCodeValid = mutableStateOf(false)
    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    val isLoading = mutableStateOf(false)

    private fun nicknameDuplicated() {
        if (!isNicknameValid()) {
            _nickname.value = nickname.value.copy(isError = true)
            return
        }
        userUseCase.invokeSingle(
            key = nickname.value.text,
            type = "nickname"
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    isLoading.value = false
                    nicknameValid.value = true
                }
                is Resource.Error -> {
                    _nickname.value = nickname.value.copy(
                        isError = true,
                        error = if (it.message == "서버 연결에 실패하였습니다.") it.message
                        else "중복된 닉네임입니다. 다시 입력해주세요."
                    )
                    isLoading.value = false
                }
                is Resource.Loading -> isLoading.value = true
            }
        }.launchIn(viewModelScope)
    }
    private fun isNicknameValid(): Boolean {
        return nickname.value.text.isNotBlank()
                && Pattern.matches("^[가-힣a-zA-Z\\d]*$" ,nickname.value.text)
                && nickname.value.text.length in 2..8
    }
    private fun emailDuplication() {
        if (!isEmailValid()) {
            _email.value = email.value.copy(isError = true)
            return
        }
        userUseCase.invokeSingle(
            key = email.value.text,
            type = "email"
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    isLoading.value = false
                    _email.value = email.value.copy(isError = false)
                    emailValid.value = true
                    if (certification.value == EMAIL_INPUT_SUCCESS)
                        certification.value = EMAIL_SEND_CERTIFICATION
                }
                is Resource.Error -> {
                    isLoading.value = false
                    _email.value = email.value.copy(
                        isError = true,
                        error = if (it.message == "서버 연결에 실패하였습니다.") it.message
                        else "중복된 이메일입니다. 다시 입력해주세요."
                    )
                }
                is Resource.Loading -> isLoading.value = true
            }
        }.launchIn(viewModelScope)
    }
    private fun emailCodeCorrect() {
        userUseCase.invokeSingle(
            key = emailCode.value.text,
            type = "emailCode"
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    isLoading.value = false
                    _emailCode.value = emailCode.value.copy(isError = false)
                    emailCodeValid.value = true
                    if (certification.value == EMAIL_SEND_CERTIFICATION)
                        certification.value = EMAIL_CERTIFICATION_INPUT_SUCCESS
                }
                is Resource.Loading -> isLoading.value = true
                is Resource.Error -> {
                    isLoading.value = false
                    emailCodeValid.value = false
                }
            }
        }.launchIn(viewModelScope)
        emailCode.value.text = ""
    }
    private fun isEmailValid(): Boolean {
        return email.value.text.isNotBlank()
                && Patterns.EMAIL_ADDRESS.matcher(email.value.text).matches()
    }
    private fun isPasswordValid(value: String): Boolean {
        return value.isNotBlank()
                && Pattern.matches("^[a-zA-Z\\d]*$", value)
                && value.length in 8..20
    }
    private fun isPasswordCorrect(): Boolean {
        return password.value.text == password2.value.text
    }

    fun isSignUpAble(): Boolean {
        val isPasswordValid = isPasswordValid(password.value.text)
                && isPasswordValid(password2.value.text) && isPasswordCorrect()
        return isNicknameValid() && isPasswordValid && isEmailValid() && emailCodeValid.value
    }
    fun emailTimerTerminated(isErrorVisible: Boolean = true) {
        _emailCode.value = emailCode.value.copy(
            error = "인증 기간이 만료되었습니다.",
            isError = isErrorVisible
        )
    }
    fun onEvent(event: SignUpEvent){
        when (event) {
            is SignUpEvent.EnteredNickname -> {
                _nickname.value = nickname.value.copy(text = event.value)
            }
            is SignUpEvent.ChangeNicknameFocus -> {
                _nickname.value = nickname.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            nickname.value.text.isBlank()
                )
            }
            is SignUpEvent.EnteredEmail -> {
                _email.value = email.value.copy(text = event.value)
                certification.value = if (isEmailValid()) EMAIL_INPUT_SUCCESS else EMAIL_INPUT
            }
            is SignUpEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            email.value.text.isBlank()
                )
            }
            is SignUpEvent.EnteredCertification -> {
                if (event.value.isBlank()) emailDuplication()
                _emailCode.value = emailCode.value.copy(
                    text = event.value,
                    isError = emailCodeValid.value
                )
                certification.value = if (emailCodeValid.value) EMAIL_CERTIFICATION_INPUT else EMAIL_SEND_CERTIFICATION
            }

            is SignUpEvent.EnteredPassword -> {
                _password.value = password.value.copy(text = event.value)
            }
            is SignUpEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password.value.text.isBlank()
                )
            }
            is SignUpEvent.EnteredPassword2 -> {
                _password2.value = password2.value.copy(text = event.value)
            }
            is SignUpEvent.ChangePassword2Focus -> {
                _password2.value = password2.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password2.value.text.isBlank()
                )
            }
            is SignUpEvent.ChangeCertification -> {
                if (event.value >= EMAIL_SEND_CERTIFICATION) {
                    if (emailCode.value.text.isNotBlank()) emailCodeCorrect()
                }
                else emailDuplication()
                if (certification.value == EMAIL_CERTIFICATION_INPUT)
                    certification.value = EMAIL_CERTIFICATION_INPUT_SUCCESS
                if (certification.value == EMAIL_SEND_CERTIFICATION) {
                    _emailCode.value = emailCode.value.copy(isError = true)
                }
            }
            is SignUpEvent.SignUp -> {
                if (isNicknameValid()) nicknameDuplicated()
                _nickname.value = nickname.value.copy(isError = !isNicknameValid())
                _email.value = email.value.copy(isError = !isEmailValid())
                if (!emailCodeValid.value) _email.value = email.value.copy(
                    isError = true,
                    error = "이메일 인증을 완료해주세요."
                )
                _emailCode.value = emailCode.value.copy(isError = !emailCodeValid.value)
                _password.value = password.value.copy(isError = !isPasswordValid(password.value.text))
                _password2.value = password2.value.copy(
                    isError = !isPasswordValid(password2.value.text)
                            || password.value.text != password2.value.text
                )
                if (isSignUpAble()) {
                    userUseCase.invoke(
                        userDTO = SignUpDTO(
                            email = email.value.text,
                            name = nickname.value.text,
                            pwd = password.value.text,
                            pwd2 = password2.value.text),
                        type = "signUp"
                    ).onEach {
                        when (it) {
                            is Resource.Success -> {
                                isLoading.value = false
                                _eventFlow.emit(UiEvent.SignUpSuccess)
                            }
                            is Resource.Loading -> isLoading.value = true
                            is Resource.Error -> {
                                isLoading.value = false
                                _eventFlow.emit(UiEvent.SignUpError("회원가입에 실패하였습니다."))
                            }
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
    sealed class UiEvent {
        data class SignUpError(val message: String): UiEvent()
        object SignUpSuccess: UiEvent()
    }
}
