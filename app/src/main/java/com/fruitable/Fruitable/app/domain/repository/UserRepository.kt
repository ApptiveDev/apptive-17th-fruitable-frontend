package com.fruitable.Fruitable.app.domain.repository

import com.fruitable.Fruitable.app.data.network.dto.user.UserBaseClass
import retrofit2.Response

interface UserRepository {
    suspend fun userMethod(userDTO: UserBaseClass, type: String): Response<String>
    suspend fun userMethodSingle(key: String, type: String): Response<String>
    suspend fun userMethodDouble(key: String, key2: String): Response<String>
}