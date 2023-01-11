package com.fruitable.Fruitable.app.data.repository

import com.fruitable.Fruitable.app.data.network.api.UserApi
import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {

    override suspend fun signUp(signUpDTO: SignUpDTO): Response<String> {
        return api.signUp(signUpDTO)
    }

}