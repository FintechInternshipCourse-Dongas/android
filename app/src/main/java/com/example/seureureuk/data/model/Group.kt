package com.example.seureureuk.data.model

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

data class GroupInfoResponse(
    val id: String,
    val groupName: String,
    val createAt: String,
    val numOfparticipantCount: Int,
    val bookmark: Boolean
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
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
