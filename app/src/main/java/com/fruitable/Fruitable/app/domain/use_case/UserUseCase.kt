package com.fruitable.Fruitable.app.domain.use_case

import com.fruitable.Fruitable.app.data.network.dto.user.UserBaseClass
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserUseCase @Inject constructor(
    private val repository: UserRepository
){
    operator fun invoke(
        userDTO: UserBaseClass,
        type: String
    ) : Flow<Resource<String>> = flow {
            try {
                emit(Resource.Loading())
                val r = repository.userMethod(userDTO, type)
                r.toString().log()
                when (r.code()) {
                    200 -> emit(Resource.Success(r.body()!!))
                    201 -> emit(Resource.Success(r.body()!!))
                    400 -> emit(Resource.Error("[ERROR] Bad Request error occurred"))
                    500 -> emit(Resource.Error("[ERROR] Internal Server error occurred"))
                    else -> emit(Resource.Error("[ERROR] An unexpected error occurred"))
                }
            } catch (e: HttpException) {
                emit(Resource.Error("[ERROR/SIGNUP] HTTP Exception occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("[ERROR/SIGNUP] IOException occurred"))
            }

    }
}