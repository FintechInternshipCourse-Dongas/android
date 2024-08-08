package com.example.seureureuk.data.api

import com.example.seureureuk.data.model.GroupInfoResponseList
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("mock/groups")
    fun getAllGroups(): Call<GroupInfoResponseList>
}
