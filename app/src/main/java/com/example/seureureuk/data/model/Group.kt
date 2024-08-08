// GroupSettlementResponse.kt
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

data class GroupSettlementResponse(
    val groupMembers: List<GroupMember>,
    val settlements: List<GroupSettlement>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.createTypedArrayList(GroupMember)!!,
        parcel.createTypedArrayList(GroupSettlement)!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeTypedList(groupMembers)
        parcel.writeTypedList(settlements)
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

data class GroupMember(
    val id: Int,
    val memberName: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(memberName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GroupMember> {
        override fun createFromParcel(parcel: Parcel): GroupMember {
            return GroupMember(parcel)
        }

        override fun newArray(size: Int): Array<GroupMember?> {
            return arrayOfNulls(size)
        }
    }
}

data class GroupSettlement(
    val id: Int,
    val settlementName: String,
    val totalPaymentAmount: Int,
    val settlementAt: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
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

    companion object CREATOR : Parcelable.Creator<GroupSettlement> {
        override fun createFromParcel(parcel: Parcel): GroupSettlement {
            return GroupSettlement(parcel)
        }

        override fun newArray(size: Int): Array<GroupSettlement?> {
            return arrayOfNulls(size)
        }
    }
}

data class GroupSettlementResponseData(
    val data: GroupSettlementResponse
)
