package com.fruitable.Fruitable.app.data.network.dto.user

import com.fruitable.Fruitable.app.data.network.dto.BaseClass

data class SignUpDTO(
    val email: String = "",
    val name: String = "",
    val pwd: String = "",
    val pwd2: String = ""
): BaseClass()
