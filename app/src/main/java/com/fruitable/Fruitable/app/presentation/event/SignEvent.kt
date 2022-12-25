package com.fruitable.Fruitable.app.presentation.event

import androidx.compose.ui.focus.FocusState

sealed class SignInEvent {
    data class EnteredEmail(val value : String) : SignInEvent()
    data class EnteredPassword(val value : String) : SignInEvent()
    data class ChangeEmailFocus(val focus : FocusState) : SignInEvent()
    data class ChangePasswordFocus(val focus : FocusState) : SignInEvent()

    object SignIn : SignInEvent()

}
