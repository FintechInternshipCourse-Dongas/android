package com.example.seureureuk.data.api

import com.example.seureureuk.data.model.GroupInfoResponse
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("api/mock/groups")
    fun getGroups(): Call<List<GroupInfoResponse>>
}
