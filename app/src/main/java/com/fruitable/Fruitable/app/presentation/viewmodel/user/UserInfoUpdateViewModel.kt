package com.fruitable.Fruitable.app.presentation.viewmodel.user

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fruitable.Fruitable.app.data.network.dto.user.NicknameDTO
import com.fruitable.Fruitable.app.data.network.dto.user.PasswordUpdateDTO
import com.fruitable.Fruitable.app.domain.use_case.UserUseCase
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import com.fruitable.Fruitable.app.presentation.event.UserInfoUpdateEvent
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
class UserInfoUpdateViewModel @Inject constructor(
    val userUseCase: UserUseCase
) : ViewModel() {
    private val _nickname = mutableStateOf(TextFieldBoxState(
        title = "닉네임",
        hint = "8자 이하의 한글, 영어, 숫자만 입력해주세요.",
        error = "올바르지 않은 닉네임입니다. 다시 입력해주세요."
    ))
    val nickname: State<TextFieldBoxState> = _nickname

    private val _password = mutableStateOf(TextFieldBoxState(
        title = "기존 비밀번호",
        error = "비밀번호가 일치하지 않습니다. 다시 입력해주세요."
    ))
    val password = _password

    private val _newPassword = mutableStateOf(TextFieldBoxState(
        title = "새 비밀번호",
        hint = "8자 이상의 비밀번호를 설정해주세요.",
        error = "적절하지 않은 비밀번호입니다. 다시 입력해주세요"
    ))
    val newPassword = _newPassword

    private val _newPassword2 = mutableStateOf(TextFieldBoxState(
        title = "새 비밀번호 확인",
        error = "비밀번호가 일치하지 않습니다. 다시 입력해주세요."
    ))
    val newPassword2 = _newPassword2

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun isNicknameUpdatable(): Boolean{
        return nickname.value.text.isNotBlank()
                && Pattern.matches("^[가-힣a-zA-Z0-9]*$" ,nickname.value.text)
                && nickname.value.text.length <= 8
    }
    private fun isPasswordValid(value: String): Boolean {
        return value.isNotBlank() && Pattern.matches("^[a-zA-Z0-9]*$", value) && value.length >= 8
    }
    fun isPasswordUpdatable(): Boolean {
        val passwordList = listOf(password.value.text, newPassword.value.text, newPassword2.value.text)
        return passwordList.all{ isPasswordValid(it) } && passwordList[1] == passwordList[2]
    }

    fun onEvent(event: UserInfoUpdateEvent){
        when (event) {
            is UserInfoUpdateEvent.EnteredNickname -> {
                _nickname.value = nickname.value.copy(
                    text = event.value
                )
            }
            is UserInfoUpdateEvent.ChangeNicknameFocus -> {
                _nickname.value = nickname.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            nickname.value.text.isBlank()
                )
            }
            is UserInfoUpdateEvent.EnteredPassword -> {
                _password.value = password.value.copy(
                    text = event.value
                )
            }
            is UserInfoUpdateEvent.ChangePasswordFocus -> {
                _password.value = password.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            password.value.text.isBlank()
                )
            }
            is UserInfoUpdateEvent.EnteredNewPassword -> {
                _newPassword.value = newPassword.value.copy(
                    text = event.value
                )
            }
            is UserInfoUpdateEvent.ChangeNewPasswordFocus -> {
                _newPassword.value = newPassword.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            newPassword.value.text.isBlank()
                )
            }
            is UserInfoUpdateEvent.EnteredNewPassword2 -> {
                _newPassword2.value = newPassword2.value.copy(
                    text = event.value
                )
            }
            is UserInfoUpdateEvent.ChangeNewPasswordFocus2 -> {
                _newPassword2.value = newPassword2.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            newPassword2.value.text.isBlank()
                )
            }
            UserInfoUpdateEvent.NicknameSave -> {
                _nickname.value = nickname.value.copy(
                    isError = !isNicknameUpdatable()
                )
                if (isNicknameUpdatable()) {
                    userUseCase.invoke(
                        userDTO = NicknameDTO(newName = nickname.value.text),
                        type = "updateName"
                    ).onEach {
                        when (it) {
                            is Resource.Success -> _eventFlow.emit(UiEvent.SaveUserNickname)
                            else -> _eventFlow.emit(UiEvent.UpdateError("닉네임 업데이트 실패"))
                        }
                    }.launchIn(viewModelScope)
                }
            }
            UserInfoUpdateEvent.PasswordSave -> {
                _password.value = password.value.copy(
                    isError = !isPasswordValid(password.value.text)
                )
                _newPassword.value = newPassword.value.copy(
                    isError = !isPasswordValid(newPassword.value.text)
                )
                _newPassword2.value = newPassword2.value.copy(
                    isError = !isPasswordValid(newPassword2.value.text)
                            || newPassword.value.text != newPassword2.value.text
                )
                if (isPasswordUpdatable()) {
                    userUseCase.invoke(
                        userDTO = PasswordUpdateDTO(
                            pwd = password.value.text,
                            newPwd = newPassword.value.text,
                            newPwd2 = newPassword2.value.text
                        ),
                        type = "updatePassword"
                    ).onEach {
                        when (it) {
                            is Resource.Success -> _eventFlow.emit(UiEvent.SaveUserPassword)
                            else -> _eventFlow.emit(UiEvent.UpdateError("패스워드 업데이트 실패"))
                        }
                    }.launchIn(viewModelScope)
                }
            }
        }
    }
    sealed class UiEvent {
        data class UpdateError(val message: String): UiEvent()
        object SaveUserNickname: UiEvent()
        object SaveUserPassword: UiEvent()
    }
}