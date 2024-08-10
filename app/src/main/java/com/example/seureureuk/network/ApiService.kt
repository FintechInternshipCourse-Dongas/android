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
import com.example.seureureuk.data.model.RequestedSettlementResponseData
import com.example.seureureuk.data.model.PointConversionRequest
import com.example.seureureuk.data.model.ResultResponseListAccountResponse
import com.example.seureureuk.data.model.ResultResponsePointConversionResponse
import com.example.seureureuk.data.model.ResultResponsePointInfoResponse
import com.example.seureureuk.data.model.ResultResponseUserLoginResponse
import com.example.seureureuk.data.model.ResultResponseVoid
import com.example.seureureuk.data.model.SettlementAddRequest
import com.example.seureureuk.data.model.SettlementAddResponseData
import com.example.seureureuk.data.model.SettlementCompletedResponseData
import com.example.seureureuk.data.model.SettlementDetailResponseData
import com.example.seureureuk.data.model.SettlementParticipantResponseData
import com.example.seureureuk.data.model.UserLoginRequest
import com.example.seureureuk.data.model.UserSignUpRequest
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.PUT


interface ApiService {
    @PUT("participant/{participantId}")
    suspend fun confirmRequestedSettlement(@Path("participantId") participantId: Int): Response<Void>

    @GET("settlements")
    suspend fun getRequestedSettlement(): Response<RequestedSettlementResponseData>

    @GET("settlements/{settlementId}")
    suspend fun getSettlementDetail(@Path("settlementId") settlementId: Int): Response<SettlementDetailResponseData>

    @POST("settlements/{settlementId}")
    suspend fun executeSettlementProcess(@Path("settlementId") settlementId: Int): Response<SettlementCompletedResponseData>

    // id: settlementId
    @GET("settlements/{id}/participants")
    suspend fun getSettlementParticipants(@Path("id") id: Int): Response<SettlementParticipantResponseData>

    @POST("settlements/{groupId}/request")
    suspend fun addSettlement(@Path("groupId") groupId: Int, @Body request: SettlementAddRequest): Response<SettlementAddResponseData>

    @POST("groups/entrance")
    suspend fun enterGroupWithInviteCode(@Body request: GroupEntranceRequest): Response<GroupEntranceResponseData>

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