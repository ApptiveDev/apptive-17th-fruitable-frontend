package com.fruitable.Fruitable.app.data.network.dto.user

data class LoginDTO(
    val email: String = "",
    val pwd: String = ""
): UserBaseClass()
