package com.fruitable.Fruitable.app.presentation.event

import androidx.compose.ui.focus.FocusState

sealed class LeaveAppEvent{
    data class EnteredPassword(val value: String): LeaveAppEvent()
    data class ChangePasswordFocus(val focusState: FocusState): LeaveAppEvent()
    data class EnteredPassword2(val value: String): LeaveAppEvent()
    data class ChangePassword2Focus(val focusState: FocusState): LeaveAppEvent()

    object LeaveApp: LeaveAppEvent()
}