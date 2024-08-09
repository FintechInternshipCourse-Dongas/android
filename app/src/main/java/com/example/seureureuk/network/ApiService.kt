package com.example.seureureuk.network

import com.example.seureureuk.data.model.AccountRegisterRequest
import com.example.seureureuk.data.model.GroupCreateRequest
import com.example.seureureuk.data.model.GroupCreateResponseData
import com.example.seureureuk.data.model.GroupEntranceRequest
import com.example.seureureuk.data.model.GroupEntranceResponseData
import com.example.seureureuk.data.model.GroupInfoResponseList
import com.example.seureureuk.data.model.GroupInviteResponseData
import com.example.seureureuk.data.model.GroupMemberResponseData
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
    @POST("groups/entrance")
    fun enterGroupWithInviteCode(@Body request: GroupEntranceRequest): Call<GroupEntranceResponseData>

    @POST("groups/{groupId}/invite")
    fun createInviteCode(@Path("groupId") groupId: Int): Call<GroupInviteResponseData>

    @POST("groups")
    suspend fun createGroup(@Body request: GroupCreateRequest): Response<GroupCreateResponseData>

    @GET("groups/{groupId}/members")
    suspend fun getGroupMembers(@Path("groupId") groupId: Int): Response<GroupMemberResponseData>

    @GET("groups/{groupId}/settlements")
    suspend fun getGroupSettlements(@Path("groupId") groupId: Int): Response<GroupSettlementResponseData>

    @GET("groups")
    fun getAllGroups(): Call<GroupInfoResponseList>

    @POST("users/register")
    fun signUpUser(@Body request: UserSignUpRequest): Call<ResultResponseVoid>

    @POST("users/auth/login")
    fun loginUser(@Body request: UserLoginRequest): Call<ResultResponseUserLoginResponse>

    @POST("accounts/register")
    fun registerAccount(@Body request: AccountRegisterRequest): Call<ResultResponseVoid>
}