package com.fruitable.Fruitable.app.presentation.event

import android.net.Uri
import androidx.compose.ui.focus.FocusState

sealed class AddSaleEvent{
    // 제목, 가격, 연락처, 사진, 해시태그, 판매기한, 내용
    data class EnteredTitle(val value: String): AddSaleEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredPrice(val value: String): AddSaleEvent()
    data class ChangePriceFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredContact(val value: String): AddSaleEvent()
    data class ChangeContactFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredDeadline(val value: String): AddSaleEvent()
    data class ChangeDeadlineFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredContent(val value: String): AddSaleEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddSaleEvent()

    data class EnteredImage(val value: List<Uri>): AddSaleEvent()
    object SaveSale: AddSaleEvent()
}