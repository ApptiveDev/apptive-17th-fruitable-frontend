package com.fruitable.Fruitable.app.data.repository

import com.fruitable.Fruitable.app.data.network.api.SalesApi
import com.fruitable.Fruitable.app.data.network.dto.sale.SaleRequestDTO
import com.fruitable.Fruitable.app.data.network.dto.sale.SaleResponseDTO
import com.fruitable.Fruitable.app.domain.repository.SaleRepository
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import javax.inject.Inject

class SaleRepositoryImpl @Inject constructor(
    private val api: SalesApi
): SaleRepository {
    override suspend fun postSale(
        saleRequestDTO: SaleRequestDTO,
        files: List<File>
    ): Response<Int> {
        val multipartBodyList = arrayListOf<MultipartBody.Part>()
        files.forEachIndexed { index, file ->
            multipartBodyList.add(
                MultipartBody.Part.createFormData(
                    name = "images",
                    filename = file.name,
                    body = file.asRequestBody("image/*".toMediaType())
                )
            )
        }
        return api.postSale(
            request = Gson().toJson(saleRequestDTO)
                .toRequestBody("application/json".toMediaTypeOrNull()),
            images = multipartBodyList
        )
    }
    override suspend fun updateSale(
        saleId: Int,
        saleRequestDTO: SaleRequestDTO,
        files: List<File>
    ): Response<String> {
        val multipartBodyList = arrayListOf<MultipartBody.Part>()
        files.forEachIndexed { index, file ->
            multipartBodyList.add(
                MultipartBody.Part.createFormData(
                    name = "images",
                    filename = file.name,
                    body = file.asRequestBody("image/*".toMediaType())
                )
            )
        }
        return api.updateSale(
            saleId = saleId,
            request = Gson().toJson(saleRequestDTO)
                .toRequestBody("application/json".toMediaTypeOrNull()),
            images = multipartBodyList
        )
    }
    override suspend fun getSales(): Response<List<SaleResponseDTO>> = api.getSales()
    override suspend fun getSale(saleId: Int): Response<SaleResponseDTO> = api.getSale(saleId)
    override suspend fun deleteSale(saleId: Int): Response<String> = api.deleteSale(saleId)
}