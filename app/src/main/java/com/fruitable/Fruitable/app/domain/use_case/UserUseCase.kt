package com.fruitable.Fruitable.app.domain.use_case

import android.content.Context
import com.fruitable.Fruitable.app.data.network.dto.BaseClass
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: UserRepository,
    @ApplicationContext val context: Context
){
    private fun useCaseInvocation(r: Response<String>): Resource<String> {
        return try {
            Resource.Loading("LOADING")
            r.body().toString().log()
            val Cookie = context.getSharedPreferences("cookie", Context.MODE_PRIVATE)
            Cookie.edit().putString("cookie", r.headers().toMultimap()["Set-Cookie"].toString()).apply()
            when (r.code()) {
                200 -> Resource.Success(r.body()!!)
                201 -> Resource.Success(r.body()!!)
                400 -> Resource.Error("[ERROR] Bad Request error occurred")
                500 -> Resource.Error("[ERROR] Internal Server error occurred")
                else -> Resource.Error("[ERROR] An unexpected error occurred")
            }
        } catch (e: HttpException) {
            Resource.Error("[ERROR/SIGNUP] HTTP Exception occurred")
        } catch (e: IOException) {
            Resource.Error("[ERROR/SIGNUP] IOException occurred")
        }
    }
    fun invoke(
        userDTO: BaseClass,
        type: String
    ) : Flow<Resource<String>> = flow {
        emit(useCaseInvocation(repository.userMethod(userDTO, type)))
    }
    fun invokeNone(type: String): Flow<Resource<String>> = flow {
        emit(useCaseInvocation(repository.userMethodNone(type)))
    }
    fun invokeSingle(
        key: String,
        type: String
    ) : Flow<Resource<String>> = flow {
        emit(useCaseInvocation(repository.userMethodSingle(key, type)))
    }
    fun invokeDouble(
        key: String,
        key2: String
    ) : Flow<Resource<String>> = flow {
        emit(useCaseInvocation(repository.userMethodDouble(key, key2)))
    }
}