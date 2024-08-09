package com.example.seureureuk.data.model

import android.os.Parcel
import android.os.Parcelable

data class GroupInfoResponse(
    val id: Int,
    val groupName: String,
    val createAt: String,
    val numOfparticipantCount: Int,
    val bookmark: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(groupName)
        parcel.writeString(createAt)
        parcel.writeInt(numOfparticipantCount)
        parcel.writeByte(if (bookmark) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupInfoResponse> {
        override fun createFromParcel(parcel: Parcel): GroupInfoResponse {
            return GroupInfoResponse(parcel)
        }

        override fun newArray(size: Int): Array<GroupInfoResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class GroupInfoResponseList(
    val data: List<GroupInfoResponse>
)

data class GroupMemberResponse(
    val id: Int,
    val memberName: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(memberName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupMemberResponse> {
        override fun createFromParcel(parcel: Parcel): GroupMemberResponse {
            return GroupMemberResponse(parcel)
        }

        override fun newArray(size: Int): Array<GroupMemberResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class GroupMemberResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: List<GroupMemberResponse>
)

data class GroupSettlementResponse(
    val id: Int,
    val settlementName: String,
    val totalPaymentAmount: Int,
    val settlementAt: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(settlementName)
        parcel.writeInt(totalPaymentAmount)
        parcel.writeString(settlementAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupSettlementResponse> {
        override fun createFromParcel(parcel: Parcel): GroupSettlementResponse {
            return GroupSettlementResponse(parcel)
        }

        override fun newArray(size: Int): Array<GroupSettlementResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class GroupSettlementResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: List<GroupSettlementResponse>
)

data class GroupCreateRequest(
    val groupName: String
)

data class GroupCreateResponse(
    val groupId: Int
)

data class GroupCreateResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: GroupCreateResponse
)

data class GroupInviteResponse(
    val invitationCode: String
)

data class GroupInviteResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: GroupInviteResponse
)

data class GroupEntranceRequest(
    val invitationCode: String
)

data class GroupEntranceResponse(
    val groupId: Int
)

data class GroupEntranceResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: GroupEntranceResponse
)

