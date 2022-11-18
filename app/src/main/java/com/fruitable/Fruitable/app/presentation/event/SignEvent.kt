package com.fruitable.Fruitable.app.presentation.event

import androidx.compose.ui.focus.FocusState

sealed class SignEvent {
    data class EnteredEmail(val value : String) : SignEvent()
    data class EnteredPassword(val value : String) : SignEvent()
    data class ChangeEmailFocus(val focus : FocusState) : SignEvent()
    data class ChangePasswordFocus(val focus : FocusState) : SignEvent()

    object SignIn : SignEvent()

}