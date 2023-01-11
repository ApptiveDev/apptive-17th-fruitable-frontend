package com.fruitable.Fruitable.app.data.network.api

import com.fruitable.Fruitable.app.data.network.dto.user.EmailCodeDTO
import com.fruitable.Fruitable.app.data.network.dto.user.EmailDTO
import com.fruitable.Fruitable.app.data.network.dto.user.NicknameDTO
import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    /**
     * 회원가입 관련
     * (회원가입 / 닉네임 중복 확인, 이메일 중복확인+코드전송, 코드 확인)
     */
    @POST("/member/save")
    suspend fun signUp(@Body signUpDTO: SignUpDTO): Response<String>
    /*
    @GET("/member/nameDuplicate")
    suspend fun nicknameValid(@Body nicknameDTO: NicknameDTO): Response<String>
    @GET("/email/send")
    suspend fun emailValid(@Body emailDTO: EmailDTO): Response<String>
    @GET("/email/confirm")
    suspend fun emailCodeValid(@Body emailCodeDTO: EmailCodeDTO): Response<String>
    */



}