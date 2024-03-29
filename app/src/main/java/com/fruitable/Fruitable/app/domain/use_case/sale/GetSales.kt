package com.fruitable.Fruitable.app.domain.use_case.sale

import com.fruitable.Fruitable.app.data.network.dto.sale.SaleResponseDTO
import com.fruitable.Fruitable.app.domain.repository.SaleRepository
import com.fruitable.Fruitable.app.domain.utils.Resource
import com.fruitable.Fruitable.app.domain.utils.log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetSales @Inject constructor(
    private val repository: SaleRepository
) {
    operator fun invoke(): Flow<Resource<List<SaleResponseDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val r = repository.getSales()
            when(r.code()) {
                200 -> emit(Resource.Success(r.body()!!))
                else -> emit(Resource.Error("전체 게시글 조회 실패"))
            }
        } catch (e: HttpException) {
            emit(Resource.Error("[ERROR/GET_SALES] HTTP Exception occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("[ERROR/GET_SALES] IOException occurred"))
        }
    }
}