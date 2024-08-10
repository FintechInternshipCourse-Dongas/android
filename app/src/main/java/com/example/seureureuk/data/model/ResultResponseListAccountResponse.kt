package com.example.seureureuk.data.model

data class ResultResponseListAccountResponse(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: List<AccountResponse>?
)

data class AccountResponse(
    val id: Int,
    val bankName: String,
    val balance: Int,
    val accountNumber: String,
    val mainAccount: Boolean,
)