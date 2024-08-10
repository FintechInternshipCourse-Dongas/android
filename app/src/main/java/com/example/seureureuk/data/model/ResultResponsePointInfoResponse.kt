package com.example.seureureuk.data.model

data class PointInfoResponse(
    val pointId: Int,
    val name: String,
    val point: Int
)

data class ResultResponsePointInfoResponse(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: PointInfoResponse?
)