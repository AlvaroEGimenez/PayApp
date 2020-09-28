package com.example.geopagos.model

import com.google.gson.annotations.SerializedName

data class PaymentsMethod(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("payment_type_id") val payment_type_id: String = "",
    @SerializedName("status") val status: String = "",
    @SerializedName("secure_thumbnail") val secure_thumbnail: String = "",
    @SerializedName("thumbnail") val thumbnail: String = "",
    @SerializedName("deferred_capture") val deferred_capture: String = "",
    @SerializedName("settings") val settings: List<Settings> = arrayListOf()
)
