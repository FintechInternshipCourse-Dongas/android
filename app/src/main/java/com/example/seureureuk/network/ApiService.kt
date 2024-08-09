package com.example.seureureuk.network

import com.example.seureureuk.data.model.GroupInfoResponseList
import com.example.seureureuk.data.model.GroupRegisterRequest
import com.example.seureureuk.data.model.GroupSettlementResponseData
import com.example.seureureuk.data.model.ResultResponseUserLoginResponse
import com.example.seureureuk.data.model.ResultResponseVoid
import com.example.seureureuk.data.model.UserLoginRequest
import com.example.seureureuk.data.model.UserSignUpRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("/api/groups")
    suspend fun createGroup(@Body groupRequest: GroupRegisterRequest): Response<Void>

    @GET("mock/groups/{groupId}")
    fun getGroupSettlement(@Path("groupId") groupId: Int): Call<GroupSettlementResponseData>

    @GET("mock/groups")
    fun getAllGroups(): Call<GroupInfoResponseList>

    @POST("users/register")
    fun signUpUser(@Body request: UserSignUpRequest): Call<ResultResponseVoid>

    @POST("users/auth/login")
    fun loginUser(@Body request: UserLoginRequest): Call<ResultResponseUserLoginResponse>
}