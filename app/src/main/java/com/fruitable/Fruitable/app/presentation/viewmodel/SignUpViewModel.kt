package com.fruitable.Fruitable.app.presentation.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.presentation.event.SignUpEvent
import com.fruitable.Fruitable.app.presentation.state.SignUpState
import com.fruitable.Fruitable.app.presentation.state.ValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel(){

    private val validateName : ValidateName = ValidateName()
    private val validateNickname: ValidateNickname = ValidateNickname()
    private val validateEmail: ValidateEmail = ValidateEmail()
    private val validatePassword: ValidatePassword = ValidatePassword()
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword()
    private val validateTerms: ValidateTerms = ValidateTerms()
    private val validateCertification : ValidateCertification = ValidateCertification()

    private val _eventFlow = MutableSharedFlow<RegisterStart>()
    val eventFlow = _eventFlow.asSharedFlow()


    var state by mutableStateOf(SignUpState(
        certificationHint = "인증번호 6자리"
    ))

    fun onEvent(event : SignUpEvent){
        when(event){
            is SignUpEvent.EnteredName -> {
                state = state.copy(name = event.value)
            }
            is SignUpEvent.EnteredNickname -> {
                state = state.copy(nickname = event.value)
            }
            is SignUpEvent.EnteredEmail -> {
                state = state.copy(email = event.value)
            }
            is SignUpEvent.EnteredPassword -> {
                state = state.copy(password = event.value)
            }
            is SignUpEvent.EnteredRepeatedPassword -> {
                state = state.copy(repeatedPassword = event.value)
            }
            is SignUpEvent.AcceptTerms -> {
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is SignUpEvent.ChangeCertificationFocus -> {
                state = state.copy(
                    certificationHintOn = !event.focus.isFocused && state.certification.isBlank()
                )
            }
            is SignUpEvent.EnteredCertification -> {
                if(state.certification.length <= 6){
                    state = state.copy(certification = event.value)
                }
                if(state.certification.length == 7){
                    state = state.copy(certification = "")
                }
            }
            SignUpEvent.SignUp -> { //signUp (버튼 눌렀을때..)
                submit()
            }
            SignUpEvent.PrevCertification -> {  //인증번호 발송 눌렀을때
                prevSubmit()
            }
            SignUpEvent.Certification -> {
                CertificationSubmit()
            }
        }
    }

    private fun submit() {
        val nameResult = validateName.execute(state.name)
        val nicknameResult = validateNickname.execute(state.nickname)
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(state.password,state.repeatedPassword)
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val isError = listOf(
            nameResult,
            nicknameResult,
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult,
        ).any{!it.successful}

        if(isError){
            state = state.copy(
                nameError = nameResult.errorMessage,
                nicknameError = nicknameResult.errorMessage,
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                termsError = termsResult.errorMessage,
            )
            return
        }else{
            viewModelScope.launch{
                _eventFlow.emit(
                    RegisterStart.Register
                )
            }
        }
    }

    fun isPrevCertifiable():Boolean{
        return validateEmail.execute(state.email).successful
    }

    private fun prevSubmit(){
        val emailResult = validateEmail.execute(state.email)

        if(!emailResult.successful){
            state = state.copy(
                emailError = emailResult.errorMessage
            )
            return
        }else{
            viewModelScope.launch {
                _eventFlow.emit(
                    RegisterStart.PrevCertification
                )
            }
        }
    }

    fun isCertifiable():Boolean{
        return validateCertification.execute(state.email).successful
    }

    fun CertificationBtnOn():Boolean{
        return state.certificationBtnOn
    }

    fun isCertificationCheck() : Boolean{
        return state.certificationCheck
    }

    private fun CertificationSubmit(){
        val certificationResult = validateCertification.execute(state.certification)

        if(!certificationResult.successful){
            state = state.copy(
                certificationError = certificationResult.errorMessage
            )
            return
        }else{
            viewModelScope.launch {
                _eventFlow.emit(
                    RegisterStart.Certification
                )
            }
        }
    }

    sealed class RegisterStart{
        object Register : RegisterStart()
        object PrevCertification : RegisterStart()
        object Certification : RegisterStart()
    }
}

class ValidateName {
    fun execute(name: String): ValidationResult {
        if(name.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "이름을 입력해주세요"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidateNickname {
    fun execute(nickname: String): ValidationResult {
        if(nickname.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "닉네임을 입력해주세요"
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "이메일을 입력해주세요"
            )
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "올바른 이메일 형식이 아닙니다."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidatePassword {
    fun execute(password: String): ValidationResult {
        if(password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "너무짧습니다."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidateRepeatedPassword {
    fun execute(password: String, repeatedPassword: String): ValidationResult {
        if(password != repeatedPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "비밀번호가 일치하지 않습니다."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidateTerms {
    fun execute(acceptedTerms: Boolean): ValidationResult {
        if(!acceptedTerms) {
            return ValidationResult(
                successful = false,
                errorMessage = "모두동의해라."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}

class ValidateCertification {
    fun execute(certification : String): ValidationResult {
        if(certification.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "인증번호를 입력해주세요"
            )
        }
        if(certification.length != 6) {
            return ValidationResult(
                successful = false,
                errorMessage = "정확한 인증번호를 6자리를 입력해주세요."
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}
