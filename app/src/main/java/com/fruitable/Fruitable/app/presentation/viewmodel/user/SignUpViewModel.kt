package com.fruitable.Fruitable.app.presentation.viewmodel.user

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.presentation.event.SignUpEvent
import com.fruitable.Fruitable.app.presentation.state.TextFieldBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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

    private val _emailCode = mutableStateOf(TextFieldBoxState())
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
     *      0. 초기 단계 ("email")
     *      1. 이메일 입력 ("email success") | example@naver.com 의 형식이 아닌 경우
     *      2. 인증번호 발송, 재발송 ("send emailCode")
     *      3. 인증번호 입력  ("certification success") | 숫자로만 구성된게 아닐 경우, 시간 초과
     *      4. 완료 -> 수정 불가
     */
    val certification = mutableStateOf(0)
    val nicknameValid = mutableStateOf(false)
    val emailValid = mutableStateOf(false)
    val emailCodeValid = mutableStateOf(false)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun nicknameDuplicated() {
        userUseCase.invokeSingle(
            key = nickname.value.text,
            type = "nickname"
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    _nickname.value = nickname.value.copy(
                        isError = !isNicknameValid(),
                        error = "적절하지 않은 닉네임입니다. 다시 입력해주세요."
                    )
                    nicknameValid.value = true
                }
                is Resource.Error -> {
                    _nickname.value = nickname.value.copy(
                        isError = true,
                        error = if (isNicknameValid()) "중복된 닉네임입니다. 다시 입력해주세요."
                            else "적절하지 않은 닉네임입니다. 다시 입력해주세요."
                    )
                }
                is Resource.Loading -> {} // nickname duplication loading
            }
        }.launchIn(viewModelScope)
    }
    private fun isNicknameValid(): Boolean {
        return nickname.value.text.isNotBlank()
                && Pattern.matches("^[가-힣a-zA-Z\\d]*$" ,nickname.value.text)
                && nickname.value.text.length in 2..8
    }
    fun emailDuplication() {
        userUseCase.invokeSingle(
            key = email.value.text,
            type = "email"
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    _email.value = email.value.copy(
                        isError = !isEmailValid(),
                        error = "잘못된 형식의 이메일입니다. 다시 입력해주세요."
                    )
                    emailValid.value = true
                    if (certification.value == 1) certification.value += 1
                }
                is Resource.Error -> {
                    _email.value = email.value.copy(
                        isError = true,
                        error = if (!isEmailValid()) "잘못된 형식의 이메일입니다. 다시 입력해주세요."
                            else "중복된 이메일입니다. 다시 입력해주세요."
                    )
                }
                is Resource.Loading -> {}
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
                    _emailCode.value = emailCode.value.copy(isError = false)
                    emailCodeValid.value = true
                    if (certification.value == 2) certification.value += 1
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        }.launchIn(viewModelScope)
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
        return isNicknameValid() && isPasswordValid && isEmailValid()
                && nicknameValid.value && emailValid.value && emailCodeValid.value
    }
    fun onEvent(event: SignUpEvent){
        when (event) {
            is SignUpEvent.EnteredNickname -> {
                _nickname.value = nickname.value.copy(
                    text = event.value
                )
            }
            is SignUpEvent.ChangeNicknameFocus -> {
                _nickname.value = nickname.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            nickname.value.text.isBlank()
                )
                if (!event.focusState.isFocused && nickname.value.text.isNotBlank())
                    nicknameDuplicated()
            }
            is SignUpEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
                certification.value = if (isEmailValid()) 1 else 0
            }
            is SignUpEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            email.value.text.isBlank()
                )
            }
            is SignUpEvent.EnteredCertification -> {
                _emailCode.value = emailCode.value.copy(
                    text = event.value,
                    isError = emailCodeValid.value
                )
                certification.value = if (emailCodeValid.value) 3 else 2
                if (emailCode.value.text.length == 6) emailCodeCorrect()
                else emailCodeValid.value = false
            }

            is SignUpEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is SignUpEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password.value.text.isBlank()
                )
            }
            is SignUpEvent.EnteredPassword2 -> {
                _password2.value = password2.value.copy(
                    text = event.value
                )
            }
            is SignUpEvent.ChangePassword2Focus -> {
                _password2.value = password2.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password2.value.text.isBlank()
                )
            }
            is SignUpEvent.ChangeCertification -> {
                if (event.value >= 2) {
                    if (emailCode.value.text.isNotBlank()) emailCodeCorrect()
                }
                else emailDuplication()
                if (certification.value == 3) certification.value += 1
                if (certification.value == 2) {
                    _emailCode.value = emailCode.value.copy(
                        isError = true,
                        error = "인증번호가 일치하지 않습니다. 다시 입력해주세요."
                    )
                }
            }
            is SignUpEvent.SignUp -> {
                if (isNicknameValid()) nicknameDuplicated()
                _nickname.value = nickname.value.copy(
                    isError = !isNicknameValid()
                )
                _email.value = email.value.copy(
                    isError = !isEmailValid()
                )
                _password.value = password.value.copy(
                    isError = !isPasswordValid(password.value.text)
                )
                _password2.value = password2.value.copy(
                    isError = !isPasswordValid(password2.value.text)
                            || password.value.text != password2.value.text
                )
                if (isSignUpAble()) {
                    viewModelScope.launch {
                        try {
                            userUseCase.invoke(
                                userDTO = SignUpDTO(
                                    email = email.value.text,
                                    name = nickname.value.text,
                                    pwd = password.value.text,
                                    pwd2 = password2.value.text),
                                type = "signUp"
                            ).collect {
                                _eventFlow.emit(UiEvent.SignUpSuccess)
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            _eventFlow.emit(UiEvent.SignUPError)
                        }
                    }
                }
            }
        }
    }
    sealed class UiEvent {
        object SignUPError: UiEvent()
        object SignUpSuccess: UiEvent()
    }
}
