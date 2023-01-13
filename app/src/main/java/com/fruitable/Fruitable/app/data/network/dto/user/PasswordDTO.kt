package com.fruitable.Fruitable.app.data.network.dto.user

import com.fruitable.Fruitable.app.data.network.dto.BaseClass

data class PasswordDTO(
    val pwd: String = "",
    val pwd2: String = ""
): BaseClass()
