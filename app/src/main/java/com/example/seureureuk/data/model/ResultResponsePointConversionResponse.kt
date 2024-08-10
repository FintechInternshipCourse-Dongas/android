package com.example.seureureuk.data.model

data class ResultResponsePointConversionResponse(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: PointConversionResponse?
)

data class PointConversionResponse(
    val balance: Int,
    val point: Int
)