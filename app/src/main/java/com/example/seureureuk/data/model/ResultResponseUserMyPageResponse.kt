package com.example.seureureuk.data.model

data class UserMyPageResponse(
    val id: Int,
    val name: String,
    val phoneNumber: String
)

data class ResultResponseUserMyPageResponse(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: UserMyPageResponse?
)