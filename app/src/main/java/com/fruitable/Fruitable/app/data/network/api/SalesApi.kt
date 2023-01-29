package com.fruitable.Fruitable.app.data.network.api

import com.fruitable.Fruitable.app.data.network.dto.sale.SaleResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SalesApi {
    /**
     * 게시글 관련
     * (게시글 작성, 전체 게시글 조회, 상세 게시글 조회, 업데이트, 삭제)
     */
    @Multipart
    @POST("/posts")
    suspend fun postSale(
        @Part("requestDto") request: RequestBody,
        @Part images: List<MultipartBody.Part?>,
    ): Response<Int>
    @GET("/posts")
    suspend fun getSales(): Response<List<SaleResponseDTO>>
    @GET("/posts/{postId}")
    suspend fun getSale(@Path("postId") saleId: Int): Response<SaleResponseDTO>
    @Multipart
    @PUT("/posts/{postId}")
    suspend fun updateSale(
        @Path("postId") saleId: Int,
        @Part("requestDto") request: RequestBody,
        @Part images: List<MultipartBody.Part?>,
    ): Response<String>
    @DELETE("/posts/{postId}")
    suspend fun deleteSale(@Path("postId") saleId: Int): Response<String>
}