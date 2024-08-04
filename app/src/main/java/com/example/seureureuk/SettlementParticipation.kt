package com.example.seureureuk

data class SettlementParticipation(
    val name: String,
    val amount: String? = null,
    val status: Boolean? = false, // 정산 요청 동의 여부
    var isSelected: Boolean? = false // 정산 참여 여부
)