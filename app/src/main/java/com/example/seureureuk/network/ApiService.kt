package com.example.seureureuk.network

import com.example.seureureuk.data.model.AccountRegisterRequest
import com.example.seureureuk.data.model.PointConversionRequest
import com.example.seureureuk.data.model.ResultResponseListAccountResponse
import com.example.seureureuk.data.model.ResultResponsePointConversionResponse
import com.example.seureureuk.data.model.ResultResponsePointInfoResponse
import com.example.seureureuk.data.model.ResultResponseUserLoginResponse
import com.example.seureureuk.data.model.ResultResponseVoid
import com.example.seureureuk.data.model.ResultResponseUserMyPageResponse
import com.example.seureureuk.data.model.UserLoginRequest
import com.example.seureureuk.data.model.UserSignUpRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("users/register")
    fun signUpUser(@Body request: UserSignUpRequest): Call<ResultResponseVoid>

    @POST("users/auth/login")
    fun loginUser(@Body request: UserLoginRequest): Call<ResultResponseUserLoginResponse>

    @GET("users")
    fun getUserMyPageInfo(): Call<ResultResponseUserMyPageResponse>

    @POST("accounts/register")
    fun registerAccount(@Body request: AccountRegisterRequest): Call<ResultResponseVoid>

    @GET("points")
    fun getPointInfo(): Call<ResultResponsePointInfoResponse>

    @GET("accounts")
    fun getAccounts(): Call<ResultResponseListAccountResponse>

    @POST("points")
    fun convertPoints(@Body request: PointConversionRequest): Call<ResultResponsePointConversionResponse>

    @DELETE("accounts/{accountId}/delete")
    fun deleteAccount(@Path("accountId") accountId: Long): Call<ResultResponseVoid>

    @POST("points/exchange")
    fun convertPoints2(@Body request: PointConversionRequest): Call<ResultResponsePointConversionResponse>
}