package com.example.geopagos.model

import com.google.gson.annotations.SerializedName

data class CardNumber (

    @SerializedName("validation") val validation : String,
    @SerializedName("length") val length : Int
)