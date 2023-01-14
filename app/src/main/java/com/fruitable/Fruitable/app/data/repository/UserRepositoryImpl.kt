package com.fruitable.Fruitable.app.data.repository

import com.fruitable.Fruitable.app.data.network.api.UserApi
import com.fruitable.Fruitable.app.data.network.dto.BaseClass
import com.fruitable.Fruitable.app.data.network.dto.user.NicknameDTO
import com.fruitable.Fruitable.app.data.network.dto.user.PasswordDTO
import com.fruitable.Fruitable.app.data.network.dto.user.PasswordUpdateDTO
import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import com.fruitable.Fruitable.app.data.storage.CookieStorage
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val cookieStore: CookieStorage,
    private val api: UserApi
) : UserRepository {
    override suspend fun userMethod(userDTO: BaseClass, type: String)
    : Response<String> {
        return when(type) {
            "updateName" -> api.updateName(userDTO as NicknameDTO)
            "updatePassword" -> api.updatePassword(userDTO as PasswordUpdateDTO)
            "leaveApp" -> api.leaveApp(userDTO as PasswordDTO)
            else -> api.signUp(userDTO as SignUpDTO)
        }
    }
    override suspend fun userMethodNone(type: String): Response<String> = api.logOut()
    override suspend fun userMethodSingle(key: String, type: String)
    : Response<String> {
        return when (type) {
            "email" -> api.emailValid(key)
            "emailCode" -> api.emailCodeValid(key)
            else -> api.nicknameValid(key)
        }
    }
    override suspend fun userMethodDouble(key: String, key2: String): Response<String> = api.logIn(key, key2)
    override fun getCooke(key: String): String = runBlocking(Dispatchers.IO) { cookieStore.getCookie(key) }
    override fun setCookie(key: String, value: String) {
        runBlocking(Dispatchers.IO) { cookieStore.setCookie(key, value) }
    }
}