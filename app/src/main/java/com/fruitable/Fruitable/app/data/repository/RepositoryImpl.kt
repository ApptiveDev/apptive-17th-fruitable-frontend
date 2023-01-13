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
    override suspend fun userMethod(userDTO: UserBaseClass, type: String)
    : Response<String> {
        return api.signUp(userDTO as SignUpDTO)
    }
    override suspend fun userMethodSingle(key: String, type: String)
    : Response<String> {
        return when (type) {
            "nickname" -> api.nicknameValid(key)
            "email" -> api.emailValid(key)
            "emailCode" -> api.emailCodeValid(key)
            else -> api.nicknameValid(key)
        }
    }
    override suspend fun userMethodDouble(key: String, key2: String)
    : Response<String> {
        return api.logIn(key, key2)
    }
}