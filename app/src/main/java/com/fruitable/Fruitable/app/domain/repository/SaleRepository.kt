package com.fruitable.Fruitable.app.domain.repository

import com.fruitable.Fruitable.app.data.network.dto.sale.SaleRequestDTO
import com.fruitable.Fruitable.app.data.network.dto.sale.SaleResponseDTO
import retrofit2.Response
import java.io.File

interface SaleRepository {
    suspend fun postSale(saleRequestDTO: SaleRequestDTO, files: List<File>): Response<Int>
    suspend fun getSales(): Response<List<SaleResponseDTO>>
    suspend fun getSale(saleId: Int): Response<SaleResponseDTO>
    suspend fun updateSale(saleId: Int, saleRequestDTO: SaleRequestDTO, files: List<File>): Response<String>
    suspend fun deleteSale(saleId: Int): Response<String>
}