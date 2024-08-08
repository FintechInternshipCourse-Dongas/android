package com.example.seureureuk.data.model

import java.time.LocalDate

data class GroupInfoResponse(
    val id: String,
    val name: String,
    val createAt: LocalDate,
    val numOfparticipantCount: Int,
    val bookmark: Boolean
)

data class GroupInfoRequest(
    val groupName: String
)