package com.fruitable.Fruitable.app.presentation.state

data class SaleTextFieldState(
    val text: String = "",
    val hint: String = "",
    val textList: List<String> = emptyList(),
    val isChecked: Boolean = false,
    val isHintVisible: Boolean = true
)