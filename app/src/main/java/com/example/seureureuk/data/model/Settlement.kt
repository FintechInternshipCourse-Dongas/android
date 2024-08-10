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
    val name: String,
    val paymentAmount: Int
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
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
    val id: Int,
    val settlementName: String,
    val totalPaymentAmount: Int,
    val groupingAt: String,
    val settlementAt: String,
    val settlementPlace: String,
    val participants: List<Participant>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createTypedArrayList(Participant) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(settlementName)
        parcel.writeInt(totalPaymentAmount)
        parcel.writeString(groupingAt)
        parcel.writeString(settlementAt)
        parcel.writeString(settlementPlace)
        parcel.writeTypedList(participants)
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

data class SettlementAddResponse(
    val settlementId: Int
)

data class SettlementAddResponseData(
    val status: String,
    val statusCode: Int,
    val message: String,
    val data: SettlementAddResponse
)


