package com.fruitable.Fruitable.app.domain.repository

import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import retrofit2.Response

interface UserRepository {
    suspend fun signUp(signUpDTO: SignUpDTO): Response<String>
}