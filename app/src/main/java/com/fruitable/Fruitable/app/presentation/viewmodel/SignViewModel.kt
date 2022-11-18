package com.fruitable.Fruitable.app.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.presentation.event.SignEvent
import com.fruitable.Fruitable.app.presentation.state.SignState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor() : ViewModel() {
    private val _eventFlow = MutableSharedFlow<RedEvent>()
    private val _signInEmail = mutableStateOf(SignState(
        hint = "아이디(이메일)"
    ))
    private val _signInPassword = mutableStateOf(SignState(
        hint = "비밀번호"
    ))

    val signInEmail : State<SignState> = _signInEmail
    val signInPassword : State<SignState> = _signInPassword
    val eventFlow = _eventFlow.asSharedFlow()

    fun error() : Boolean{
        val exceptionMap = ExceptionMap()
        return exceptionMap.all {it.value.second}
    }

    fun ExceptionMap() : MutableMap<String, Pair<String,Boolean>>{
        return mutableMapOf(
            "email" to Pair("아이디를 입력해주세요.",signInEmail.value.text.isNotBlank()),
            "password" to Pair("비밀번호를 입력해주세요.",signInPassword.value.text.isNotBlank())
        )
    }

    fun onEvent(event : SignEvent){
        when(event){
            is SignEvent.ChangeEmailFocus -> {
                _signInEmail.value = signInEmail.value.copy(
                    hintOn = !event.focus.isFocused && signInEmail.value.text.isBlank()
                )
            }
            is SignEvent.ChangePasswordFocus -> {
                _signInPassword.value = signInPassword.value.copy(
                    hintOn = !event.focus.isFocused && signInPassword.value.text.isBlank()
                )
            }
            is SignEvent.EnteredEmail -> {
                _signInEmail.value = signInEmail.value.copy(
                    text = event.value
                )
            }
            is SignEvent.EnteredPassword -> {
                _signInPassword.value = signInPassword.value.copy(
                    text = event.value
                )
            }
            SignEvent.SignIn -> {
                val exceptMap = ExceptionMap()
                val isCanLogin = exceptMap.all{it.value.second}

                if(!isCanLogin){
                   exceptMap.filter{it.value.second}.firstNotNullOf { (key,value) ->
                       viewModelScope.launch {
                           _eventFlow.emit(
                               RedEvent.ShowText(value.first)
                           )
                       }
                   }
                }else{
                    viewModelScope.launch{
                        _eventFlow.emit(
                            RedEvent.login
                        )
                    }
                }
            }
        }
    }

    sealed class RedEvent{
        data class ShowText(val message : String) : RedEvent()
        object login : RedEvent()
    }
}