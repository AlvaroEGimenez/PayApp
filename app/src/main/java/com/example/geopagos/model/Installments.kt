package com.example.geopagos.model

import com.google.gson.annotations.SerializedName

data class Installments(

    @SerializedName("payment_method_id") val payment_method_id : String,
    @SerializedName("payment_type_id") val payment_type_id : String,
    @SerializedName("issuer") val issuer : Issuer,
    @SerializedName("processing_mode") val processing_mode : String,
    @SerializedName("merchant_account_id") val merchant_account_id : String,
    @SerializedName("payer_costs") val payer_costs : ArrayList<PayerCosts>,
    @SerializedName("agreements") val agreements : String
){
    constructor():this("","",
        Issuer(),"","",
        arrayListOf(PayerCosts()),"")
}