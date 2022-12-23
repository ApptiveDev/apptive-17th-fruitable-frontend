package com.fruitable.Fruitable.app.presentation.event

import androidx.compose.ui.focus.FocusState

sealed class UserInfoUpdateEvent{
    data class EnteredNickname(val value: String): UserInfoUpdateEvent()
    data class ChangeNicknameFocus(val focusState: FocusState): UserInfoUpdateEvent()
    data class EnteredPassword(val value: String): UserInfoUpdateEvent()
    data class ChangePasswordFocus(val focusState: FocusState): UserInfoUpdateEvent()
    data class EnteredNewPassword(val value: String): UserInfoUpdateEvent()
    data class ChangeNewPasswordFocus(val focusState: FocusState): UserInfoUpdateEvent()
    data class EnteredNewPassword2(val value: String): UserInfoUpdateEvent()
    data class ChangeNewPasswordFocus2(val focusState: FocusState): UserInfoUpdateEvent()

    object NicknameSave: UserInfoUpdateEvent()
    object PasswordSave: UserInfoUpdateEvent()
}