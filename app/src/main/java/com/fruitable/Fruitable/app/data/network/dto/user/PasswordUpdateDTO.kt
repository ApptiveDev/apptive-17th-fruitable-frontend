package com.fruitable.Fruitable.app.data.network.dto.user

import com.fruitable.Fruitable.app.data.network.dto.BaseClass

data class PasswordUpdateDTO(
    val pwd: String = "",
    val newPwd: String = "",
    val newPwd2: String = ""
): BaseClass()
