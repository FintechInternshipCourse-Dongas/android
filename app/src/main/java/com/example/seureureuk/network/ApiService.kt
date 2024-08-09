package com.example.seureureuk.network

import com.example.seureureuk.data.model.AccountRegisterRequest
import com.example.seureureuk.data.model.ResultResponseUserLoginResponse
import com.example.seureureuk.data.model.ResultResponseVoid
import com.example.seureureuk.data.model.ResultResponseUserMyPageResponse
import com.example.seureureuk.data.model.UserLoginRequest
import com.example.seureureuk.data.model.UserSignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST("users/register")
    fun signUpUser(@Body request: UserSignUpRequest): Call<ResultResponseVoid>

    @POST("users/auth/login")
    fun loginUser(@Body request: UserLoginRequest): Call<ResultResponseUserLoginResponse>

    @GET("users")
    fun getUserMyPageInfo(): Call<ResultResponseUserMyPageResponse>

    @POST("accounts/register")
    fun registerAccount(@Body request: AccountRegisterRequest): Call<ResultResponseVoid>
}