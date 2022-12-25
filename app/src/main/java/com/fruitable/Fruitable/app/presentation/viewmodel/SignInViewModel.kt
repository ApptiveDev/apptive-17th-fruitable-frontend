package com.fruitable.Fruitable.app.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.presentation.event.SignInEvent
import com.fruitable.Fruitable.app.presentation.state.SignInState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor() : ViewModel() {

    private val _eventFlow = MutableSharedFlow<LoginStart>()
    private val _signInEmail = mutableStateOf(SignInState(
        hint = "아이디(이메일)"
    ))
    private val _signInPassword = mutableStateOf(SignInState(
        hint = "비밀번호"
    ))

    val signInEmail : State<SignInState> = _signInEmail
    val signInPassword : State<SignInState> = _signInPassword


    val eventFlow = _eventFlow.asSharedFlow()

    fun isLoginable(): Boolean {
        val exceptMap = LoginData()
        return exceptMap.all{it.value.second}
    }
    fun onEvent(event : SignInEvent){
        when(event){
            is SignInEvent.ChangeEmailFocus -> {
                _signInEmail.value = signInEmail.value.copy(
                    hintOn = !event.focus.isFocused && signInEmail.value.text.isBlank()
                )
            }
            is SignInEvent.ChangePasswordFocus -> {
                _signInPassword.value = signInPassword.value.copy(
                    hintOn = !event.focus.isFocused && signInPassword.value.text.isBlank()
                )
            }
            is SignInEvent.EnteredEmail -> {
                _signInEmail.value = signInEmail.value.copy(
                    text = event.value
                )
            }
            is SignInEvent.EnteredPassword -> {
                _signInPassword.value = signInPassword.value.copy(
                    text = event.value
                )
            }
            SignInEvent.SignIn -> {
                val exceptMap = LoginData()
                val isCanLogin = exceptMap.all{it.value.second}
                val emailValue = if(!exceptMap["email"]!!.second) exceptMap["email"]!!.first
                                 else null
                val passwordValue = if(!exceptMap["password"]!!.second) exceptMap["password"]?.first
                                    else null
                if(!isCanLogin){
                    _signInEmail.value = signInEmail.value.copy(
                        errorMessage = emailValue
                    )
                    _signInPassword.value = signInPassword.value.copy(
                        errorMessage = passwordValue
                    )
                    /*exceptMap.filter{it.value.second}.firstNotNullOf { (key,value) ->
                       viewModelScope.launch {
                           _eventFlow.emit(
                               LoginStart.LoginError(value.first)
                           )
                       }
                    }*/
                }else{
                    viewModelScope.launch{
                        _eventFlow.emit(
                            LoginStart.login
                        )
                    }
                }
            }
        }
    }

    fun LoginData() : MutableMap<String, Pair<String,Boolean>>{
        return mutableMapOf(
            "email" to Pair("아이디를 입력해주세요.",signInEmail.value.text.isNotBlank()),
            "password" to Pair("비밀번호를 입력해주세요.",signInPassword.value.text.isNotBlank())
        )
    }

    sealed class LoginStart{
        //data class LoginError(val message : String) : LoginStart()
        object login : LoginStart()
    }
}