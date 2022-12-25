package com.fruitable.Fruitable.app.presentation.state

data class SignInState(
    val text : String = "",
    val hint : String = "",
    val hintOn : Boolean = true,
    val errorMessage : String ?= null,
)