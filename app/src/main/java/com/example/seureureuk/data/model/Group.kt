package com.example.seureureuk.data.model

import java.time.LocalDate

data class GroupInfoResponse(
    val id: String,
    val groupName: String,
    val createAt: String,
    val numOfparticipantCount: Int,
    val bookmark: Boolean
)

data class GroupInfoResponseList(
    val data: List<GroupInfoResponse>
)
