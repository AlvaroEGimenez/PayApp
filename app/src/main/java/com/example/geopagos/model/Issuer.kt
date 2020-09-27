package com.example.geopagos.model

import com.google.gson.annotations.SerializedName

data class Issuer (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("secure_thumbnail") val secure_thumbnail : String,
    @SerializedName("thumbnail") val thumbnail : String
)