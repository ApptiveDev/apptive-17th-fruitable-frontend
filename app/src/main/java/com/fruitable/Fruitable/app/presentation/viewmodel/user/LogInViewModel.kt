package com.fruitable.Fruitable.app.presentation.viewmodel.user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.event.LogInEvent
import com.fruitable.Fruitable.app.presentation.state.TextFieldBoxState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    val userUseCase: UserUseCase
) : ViewModel() {

    private val _email = mutableStateOf(
        TextFieldBoxState(hint = "아이디 (이메일)")
    )
    val email: State<TextFieldBoxState> = _email

    private val _password = mutableStateOf(
        TextFieldBoxState(hint = "비밀번호")
    )
    val password = _password
    val errorMessage = mutableStateOf("")

    private val _eventFlow = MutableSharedFlow<LogInUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
    fun isLoginAble(): Boolean {
        return email.value.text.isNotBlank() && password.value.text.isNotBlank()
    }
    fun onEvent(event : LogInEvent){
        when(event){
            is LogInEvent.EnteredEmail -> {
                _email.value = email.value.copy(text = event.value)
            }
            is LogInEvent.ChangeEmailFocus -> {
                _email.value = email.value.copy(
                    isHintVisible = !event.focus.isFocused
                            && email.value.text.isBlank()
                )
            }
            is LogInEvent.EnteredPassword -> {
                _password.value = password.value.copy(text = event.value)
            }
            is LogInEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focus.isFocused
                            && password.value.text.isBlank()
                )
            }
            LogInEvent.SignIn -> {
                _email.value = email.value.copy(isError = email.value.text.isBlank())
                _password.value = password.value.copy(
                    isError = email.value.text.isNotBlank() && password.value.text.isBlank()
                )
                if (email.value.text.isBlank()) errorMessage.value = "아이디를 입력해주세요."
                else if (password.value.text.isBlank()) errorMessage.value = "비밀번호를 입력해주세요."
                else {
                    userUseCase.invokeDouble(
                        key = email.value.text,
                        key2 = password.value.text
                    ).onEach {
                        when (it) {
                            is Resource.Success -> _eventFlow.emit(LogInUiEvent.LogInSuccess)
                            is Resource.Error -> {
                                errorMessage.value = "일치하는 회원 정보가 없습니다. 다시 시도해주세요."
                                _eventFlow.emit(LogInUiEvent.LogInError)
                            }
                            is Resource.Loading -> {}
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }

    sealed class LogInUiEvent{
        object LogInError : LogInUiEvent()
        object LogInSuccess : LogInUiEvent()
    }
}