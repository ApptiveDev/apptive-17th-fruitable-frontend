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

class GetSale @Inject constructor(
    private val repository: SaleRepository
) {
    operator fun invoke(saleId: Int): Flow<Resource<SaleResponseDTO>> = flow {
        try {
            emit(Resource.Loading())
            val r = repository.getSale(saleId)
            when(r.code()) {
                200 -> emit(Resource.Success(r.body()!!))
                else -> r.errorBody().toString().log()
            }
        } catch (e: HttpException) {
            emit(Resource.Error("[ERROR/GET_SALE] HTTP Exception occurred"))
        } catch (e: IOException) {
            emit(Resource.Error("[ERROR/GET_SALE] IOException occurred"))
        }
    }
}