package com.fruitable.Fruitable.app.domain.repository

import com.fruitable.Fruitable.app.data.network.dto.BaseClass
import com.fruitable.Fruitable.app.data.network.dto.user.UserDTO
import retrofit2.Response

interface UserRepository {
    suspend fun userMethod(userDTO: BaseClass, type: String): Response<String>
    suspend fun userMethodNone(type: String): Response<String>
    suspend fun userMethodSingle(key: String, type: String): Response<String>
    suspend fun userMethodDouble(key: String, key2: String): Response<String>
    fun getCooke(key: String): String
    fun setCookie(key: String, value: String)
}