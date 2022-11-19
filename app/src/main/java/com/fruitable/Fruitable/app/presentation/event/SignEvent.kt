package com.fruitable.Fruitable.app.presentation.event

import androidx.compose.ui.focus.FocusState
import com.fruitable.Fruitable.app.presentation.state.SignUpState

sealed class SignInEvent {
    data class EnteredEmail(val value : String) : SignInEvent()
    data class EnteredPassword(val value : String) : SignInEvent()
    data class ChangeEmailFocus(val focus : FocusState) : SignInEvent()
    data class ChangePasswordFocus(val focus : FocusState) : SignInEvent()

    object SignIn : SignInEvent()

}


sealed class SignUpEvent{
    /*
    data class ChangeNameFocus(val focus: FocusState) : SignUpEvent()
    data class ChangeNicknameFocus(val focus: FocusState) : SignUpEvent()
    data class ChangeEmailFocus(val focus: FocusState) : SignUpEvent()
    data class ChangePasswordFocus(val focus: FocusState) : SignUpEvent()
    data class ChangeRepeatedPasswordFocus(val focus: FocusState) : SignUpEvent()
*/
    data class EnteredName(val value : String) : SignUpEvent()
    data class EnteredNickname(val value : String) : SignUpEvent()
    data class EnteredEmail(val value : String) : SignUpEvent()
    data class EnteredPassword(val value : String) : SignUpEvent()
    data class EnteredRepeatedPassword(val value : String) : SignUpEvent()
    data class AcceptTerms(val isAccepted: Boolean) : SignUpEvent()

    object SignUp : SignUpEvent()
}