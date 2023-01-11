package com.fruitable.Fruitable.app.domain.use_case.user

import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import com.fruitable.Fruitable.app.domain.repository.UserRepository
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignUp @Inject constructor(
    private val repository: UserRepository
){
    operator fun invoke(
        signUpDTO: SignUpDTO
    ) : Flow<Resource<String>> = flow {
            try {
                emit(Resource.Loading())
                val r = repository.signUp(signUpDTO)
                when (r.code()) {
                    200 -> {
                        "회원가입 성공 in repository".log()
                        emit(Resource.Success(r.body()!!))
                    }
                    400 -> emit(Resource.Error("[ERROR/SIGNUP] Server error"))
                    500 -> emit(Resource.Error("[ERROR/SIGNUP] Request body is wrong"))
                    else -> emit(Resource.Error("[ERROR/SIGNUP] An unexpected error occurred"))
                }

            } catch (e: HttpException) {
                emit(Resource.Error("[ERROR/SIGNUP] HTTP Exception occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("[ERROR/SIGNUP] IOException occurred"))
            }

    }
}