package com.fruitable.Fruitable.app.data.network.api

import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("/member/save")
    suspend fun signUp(
        @Body signUpDTO: SignUpDTO
    ): Response<String>
}