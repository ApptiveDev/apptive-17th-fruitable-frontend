package com.fruitable.Fruitable.app.presentation.state

import android.net.Uri

data class SaleTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)
data class ImageState(
    var listOfSelectedImages:List<Uri> = emptyList()
)

data class HashTagState(
    val text: List<String> = emptyList(),
    val hint: String = "",
    val isHintVisible: Boolean = true
)

data class DeadlineState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val isChecked: Boolean = false
)