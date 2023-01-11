package com.fruitable.Fruitable.app.data.repository

import com.fruitable.Fruitable.app.data.network.api.UserApi
import com.fruitable.Fruitable.app.data.network.dto.user.*
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: UserApi
) : UserRepository {
    override suspend fun userMethod(
        userDTO: UserBaseClass,
        type: String
    ): Response<String> {
        return when (type) {
            "signUp" -> api.signUp(userDTO as SignUpDTO)
            /*"nicknameValid" -> api.nicknameValid(userDTO as NicknameDTO)
            "emailValid" -> api.emailValid(userDTO as EmailDTO)
            "emailCodeValid" -> api.emailCodeValid(userDTO as EmailCodeDTO)*/
            else -> api.signUp(userDTO as SignUpDTO)
        }
    }
}