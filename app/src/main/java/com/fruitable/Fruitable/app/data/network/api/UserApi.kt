package com.fruitable.Fruitable.app.data.network.api

import com.fruitable.Fruitable.app.data.network.dto.user.SignUpDTO
import com.fruitable.Fruitable.app.data.network.dto.user.NicknameDTO
import com.fruitable.Fruitable.app.data.network.dto.user.PasswordDTO
import com.fruitable.Fruitable.app.data.network.dto.user.PasswordUpdateDTO
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    /**
     * 회원가입 관련
     * (회원가입 / 닉네임 중복 확인, 이메일 중복확인+코드전송, 코드 확인)
     */
    @POST("/member/save")
    suspend fun signUp(@Body signUpDTO: SignUpDTO): Response<String>
    @GET("/member/nameDuplicate")
    suspend fun nicknameValid(@Query("name") name: String): Response<String>
    @GET("/email/send")
    suspend fun emailValid(@Query("email") email: String): Response<String>
    @GET("/email/confirm")
    suspend fun emailCodeValid(@Query("emailCode") emailCode: String): Response<String>

    /**
     * 로그인 및 사용자 정보 관리
     * (로그인, 로그아웃, 닉네임 수정, 비밀번호 수정, 회원 탈퇴)
     */
    @GET("/member/login")
    suspend fun logIn(
        @Query("email") email: String,
        @Query("pwd") pwd: String
    ): Response<String>
    @GET("/member/logout")
    suspend fun logOut(): Response<String>
    @PUT("/member/updateName")
    suspend fun updateName(@Body nicknameDTO: NicknameDTO): Response<String>
    @PUT("/member/updatePwd")
    suspend fun updatePassword(@Body passwordUpdateDTO: PasswordUpdateDTO): Response<String>
    @HTTP(method="DELETE", path="/member/delete", hasBody=true)
    suspend fun leaveApp(@Body passwordDTO: PasswordDTO): Response<String>

}