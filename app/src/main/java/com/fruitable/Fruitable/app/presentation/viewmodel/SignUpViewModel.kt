package com.fruitable.Fruitable.app.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
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

    private val _number = mutableStateOf(TextFieldBoxState())
    val number: State<TextFieldBoxState> = _number

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
     *      2. 인증번호 발송, 재발송 ("send number")
     *      3. 인증번호 입력  ("certification success") | 숫자로만 구성된게 아닐 경우, 시간 초과
     *      4. 완료 -> 수정 불가
     */
    val certification = mutableStateOf(0)

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun isNicknameDuplicated(): Boolean {
        var returnValue = true
        userUseCase.invokeSingle(
            key = nickname.value.text,
            type = "nicknameValid"
        ).onEach {
            when (it) {
                is Resource.Success -> {
                    returnValue = false
                    "nickname is available".log()
                }
                is Resource.Error -> "[ERROR] nickname is duplicated".log()
                is Resource.Loading -> "nickname duplication loading".log()
            }
        }.launchIn(viewModelScope)

        return returnValue
    }
    private fun isNicknameValid(): Boolean {
        return nickname.value.text.isNotBlank()
                && Pattern.matches("^[가-힣a-zA-Z\\d]*$" ,nickname.value.text)
                && nickname.value.text.length in 2..8
                && !isNicknameDuplicated()
    }
    private fun isEmailValid(): Boolean {
        return Pattern.matches("^[a-zA-Z0-9]*@[a-zA-Z\\d.]*$",
            email.value.text)
    }
    private fun isNumberValid(): Boolean {
        return number.value.text.length == 6
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
        return isNicknameValid() && isPasswordValid && isEmailValid() && isNumberValid()
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
                _number.value = number.value.copy(
                    text = event.value
                )
                certification.value = if (isNumberValid()) 3 else 2
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
                _email.value = email.value.copy(
                    isError = !isEmailValid()
                )
                if (event.value >= 2){
                    _number.value = number.value.copy(
                        isError = !isNumberValid()
                    )
                }
                if (certification.value == 1 || certification.value == 3) certification.value += 1
            }
            is SignUpEvent.SignUp -> {
                _nickname.value = nickname.value.copy(
                    isError = !isNicknameValid()
                )
                _email.value = email.value.copy(
                    isError = !isEmailValid()
                )
                _number.value = number.value.copy(
                    isError = !isNumberValid()
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
