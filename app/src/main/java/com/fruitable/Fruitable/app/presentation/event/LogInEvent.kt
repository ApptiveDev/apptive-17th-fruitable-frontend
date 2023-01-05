package com.fruitable.Fruitable.app.presentation.event

import androidx.compose.ui.focus.FocusState

sealed class LogInEvent {
    data class EnteredEmail(val value : String) : LogInEvent()
    data class EnteredPassword(val value : String) : LogInEvent()
    data class ChangeEmailFocus(val focus : FocusState) : LogInEvent()
    data class ChangePasswordFocus(val focus : FocusState) : LogInEvent()

    object SignIn : LogInEvent()

}
