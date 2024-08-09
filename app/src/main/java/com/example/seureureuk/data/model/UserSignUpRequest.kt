package com.example.seureureuk.data.model

data class UserSignUpRequest(
    val email: String,
    val password: String,
    val name: String,
    val phoneNumber: String
)