package com.fruitable.Fruitable.app.presentation.event

import android.net.Uri
import androidx.compose.ui.focus.FocusState

sealed class AddSaleEvent{
    data class EnteredCategory(val value: String): AddSaleEvent()
    data class EnteredTitle(val value: String): AddSaleEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredPrice(val value: String): AddSaleEvent()
    data class ChangePriceFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredContact(val value: String): AddSaleEvent()
    data class ChangeContactFocus(val focusState: FocusState): AddSaleEvent()
    data class EnteredContent(val value: String): AddSaleEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredImage(val value: Uri?): AddSaleEvent()
    data class RemoveImage(val value: Uri?): AddSaleEvent()
    data class EnteredHashTag(val value: String): AddSaleEvent()
    data class ChangeHashTagFocus(val focusState: FocusState): AddSaleEvent()
    data class RemoveHashTag(val value: String): AddSaleEvent()
    data class EnterDeadLine(val value: String): AddSaleEvent()
    object ChangeDeadLine: AddSaleEvent()
    object SaveSale: AddSaleEvent()
}