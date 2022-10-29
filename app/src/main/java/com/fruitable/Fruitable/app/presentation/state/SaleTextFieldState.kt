package com.fruitable.Fruitable.app.presentation.state

data class SaleTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)