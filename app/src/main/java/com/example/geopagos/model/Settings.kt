package com.example.geopagos.model

import com.google.gson.annotations.SerializedName

class Settings(
    @SerializedName("card_number") val card_number: CardNumber,
    @SerializedName("bin") val bin: Bin,
    @SerializedName("security_code") val security_code: SecurityCode
)