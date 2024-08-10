package com.example.seureureuk.data.model

import android.os.Parcel
import android.os.Parcelable

data class Settlement(
    val name: String,
    val amount: Int,
    val date: String
)

data class Participant(
    val id: Int,
    val participantName: String,
    val paymentAmount: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(participantName)
        parcel.writeInt(paymentAmount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Participant> {
        override fun createFromParcel(parcel: Parcel): Participant {
            return Participant(parcel)
        }

        override fun newArray(size: Int): Array<Participant?> {
            return arrayOfNulls(size)
        }
    }
}

data class SettlementAddRequest(
    val settlementName: String,
    val totalAmount: Int,
    val groupingAt: String,
    val settlementAt: String,
    val settlementPlace: String,
    val participants: List<Participant>
)

fun createSettlementAddRequest(
    settlementName: String,
    totalAmount: Int,
    groupingAt: String,
    settlementAt: String,
    settlementPlace: String,
    participants: ArrayList<GroupMemberResponse>
): SettlementAddRequest {
    // 선택된 멤버만 필터링
    val selectedMembers = participants.filter { it.isSelected == true }

    // 총 금액을 선택된 멤버 수로 나누기
    val individualAmount = if (selectedMembers.isNotEmpty()) {
        totalAmount / selectedMembers.size
    } else {
        0
    }

    // Participant 객체 생성
    val participants = selectedMembers.map { member ->
        Participant(
            id = member.id,
            participantName = member.memberName,
            paymentAmount = individualAmount
        )
    }

    // SettlementAddRequest 생성
    return SettlementAddRequest(
        settlementName = settlementName,
        totalAmount = totalAmount,
        groupingAt = groupingAt,
        settlementAt = settlementAt,
        settlementPlace = settlementPlace,
        participants = participants
    )
}

data class SettlementAddResponse(
    val id: Int
)

data class SettlementAddResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: SettlementAddResponse
)

data class SettlementParticipantResponse(
    val id: Int,
    val participantName: String,
    val paymentAmount: Int,
    val agreementStatus: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte() // Boolean 값을 byte로 변환해서 읽음
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(participantName)
        parcel.writeInt(paymentAmount)
        parcel.writeByte(if (agreementStatus) 1 else 0) // Boolean 값을 byte로 변환해서 씀
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<SettlementParticipantResponse> {
        override fun createFromParcel(parcel: Parcel): SettlementParticipantResponse {
            return SettlementParticipantResponse(parcel)
        }

        override fun newArray(size: Int): Array<SettlementParticipantResponse?> {
            return arrayOfNulls(size)
        }
    }
}


data class SettlementParticipantResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: List<SettlementParticipantResponse>
)
