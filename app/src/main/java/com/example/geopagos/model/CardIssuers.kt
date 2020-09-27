package com.example.geopagos.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class CardIssuers(

    @SerializedName("id") val id: Int = 0,
    @SerializedName("name") val name: String = "",
    @SerializedName("secure_thumbnail") val secure_thumbnail: String = "",
    @SerializedName("thumbnail") val thumbnail: String = " ",
    @SerializedName("processing_mode") val processing_mode: String = "",
    @SerializedName("merchant_account_id") val merchant_account_id: String = ""
) : Serializable
