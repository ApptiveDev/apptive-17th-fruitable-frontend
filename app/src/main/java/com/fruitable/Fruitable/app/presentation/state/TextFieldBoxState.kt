package com.fruitable.Fruitable.app.presentation.state

data class TextFieldBoxState(
    val title: String = "",
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true,
    val error: String = "",
    val isError: Boolean = false
)