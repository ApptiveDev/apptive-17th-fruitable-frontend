package com.fruitable.Fruitable.app.domain.use_case.sale

import com.fruitable.Fruitable.app.data.network.dto.sale.SaleRequestDTO
import com.fruitable.Fruitable.app.domain.repository.SaleRepository
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PostSale @Inject constructor(
    val repository: SaleRepository
) {
    operator fun invoke(saleRequestDTO: SaleRequestDTO, files: List<File>): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading())
            val r = repository.postSale(saleRequestDTO, files)
            when(r.code()) {
                201 -> emit(Resource.Success(r.body()!!))
                else -> r.errorBody().toString().log()
            }
        } catch (e: HttpException) {
            emit(Resource.Error("[ERROR/POST_SALE] HTTP Exception occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("[ERROR/POST_SALE] IOException occurred"))
        }
    }
}