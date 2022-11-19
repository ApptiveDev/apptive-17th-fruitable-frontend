package com.fruitable.Fruitable.app.presentation.state

data class SignInState(
    val text : String = "",
    val hint : String = "",
    val hintOn : Boolean = true,
    val errorMessage : String ?= null,
)

data class SignUpState(
    val name: String = "",
    val nameError: String? = null,
    val nickname: String = "",
    val nicknameError: String? = null,
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val passwordError: String? = null,
    val repeatedPassword: String = "",
    val repeatedPasswordError: String? = null,
    val acceptedTerms: Boolean = false,
    val termsError: String? = null
)

data class ValidationResult(
    val errorMessage: String? = null,
    val successful: Boolean,
)