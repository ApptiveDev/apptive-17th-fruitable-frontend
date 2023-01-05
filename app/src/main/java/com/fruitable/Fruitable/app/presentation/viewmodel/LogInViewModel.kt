package com.fruitable.Fruitable.app.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.presentation.event.LogInEvent
import com.fruitable.Fruitable.app.presentation.state.TextFieldBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor() : ViewModel() {

    private val _email = mutableStateOf(
        TextFieldBoxState(hint = "아이디 (이메일)")
    )
    val email: State<TextFieldBoxState> = _email

    private val _password = mutableStateOf(
        TextFieldBoxState(hint = "비밀번호")
    )
    val password = _password

    private val _eventFlow = MutableSharedFlow<LogInUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun onEvent(event : LogInEvent){
        when(event){
            is LogInEvent.EnteredEmail -> {
                _email.value = email.value.copy(
                    text = event.value
                )
            }
            is LogInEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isHintVisible = !event.focus.isFocused
                            && email.value.text.isBlank()
                )
            }
            is LogInEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is LogInEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focus.isFocused
                            && password.value.text.isBlank()
                )
            }
            LogInEvent.SignIn -> {
                viewModelScope.launch {
                    _eventFlow.emit(LogInUiEvent.LogInSuccess)
                }
            }
        }
    }

    sealed class LogInUiEvent{
        data class LogInError(val message : String) : LogInUiEvent()
        object LogInSuccess : LogInUiEvent()
    }
}