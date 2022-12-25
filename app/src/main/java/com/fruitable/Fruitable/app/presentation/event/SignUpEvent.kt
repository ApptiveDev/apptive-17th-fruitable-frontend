package com.fruitable.Fruitable.app.presentation.event

import androidx.compose.ui.focus.FocusState

sealed class SignUpEvent {
    data class EnteredNickname(val value: String): SignUpEvent()
    data class ChangeNicknameFocus(val focusState: FocusState): SignUpEvent()
    data class EnteredEmail(val value: String): SignUpEvent()
    data class ChangeEmailFocus(val focusState: FocusState): SignUpEvent()
    data class EnteredCertification(val value: String): SignUpEvent()
    data class ChangeCertification(val value: Int): SignUpEvent()
    data class EnteredPassword(val value: String): SignUpEvent()
    data class ChangePasswordFocus(val focusState: FocusState): SignUpEvent()
    data class EnteredPassword2(val value: String): SignUpEvent()
    data class ChangePassword2Focus(val focusState: FocusState): SignUpEvent()

    object SignUp : SignUpEvent()
}