package com.example.geopagos.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class CardIssuers (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("secure_thumbnail") val secure_thumbnail : String,
    @SerializedName("thumbnail") val thumbnail : String,
    @SerializedName("processing_mode") val processing_mode : String,
    @SerializedName("merchant_account_id") val merchant_account_id : String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(secure_thumbnail)
        parcel.writeString(thumbnail)
        parcel.writeString(processing_mode)
        parcel.writeString(merchant_account_id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CardIssuers> {
        override fun createFromParcel(parcel: Parcel): CardIssuers {
            return CardIssuers(parcel)
        }

        override fun newArray(size: Int): Array<CardIssuers?> {
            return arrayOfNulls(size)
        }
    }
}