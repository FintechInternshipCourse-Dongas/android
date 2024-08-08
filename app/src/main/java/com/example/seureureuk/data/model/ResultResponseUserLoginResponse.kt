package com.example.seureureuk.data.model

data class UserLoginResponse(
    val userId: Long,
    val accessToken: String
)

data class ResultResponseUserLoginResponse(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: UserLoginResponse?
)