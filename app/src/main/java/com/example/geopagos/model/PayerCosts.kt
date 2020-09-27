package com.example.geopagos.model

import com.google.gson.annotations.SerializedName

data class PayerCosts (

    @SerializedName("installments") val installments : Int = 0,
    @SerializedName("installment_rate") val installment_rate : String = "",
    @SerializedName("discount_rate") val discount_rate : Int = 0,
    @SerializedName("reimbursement_rate") val reimbursement_rate : String  ="",
    @SerializedName("labels") val labels : ArrayList<String> = arrayListOf(),
    @SerializedName("installment_rate_collector") val installment_rate_collector : ArrayList<String> = arrayListOf(),
    @SerializedName("min_allowed_amount") val min_allowed_amount : Int = 0,
    @SerializedName("max_allowed_amount") val max_allowed_amount : Int = 0,
    @SerializedName("recommended_message") val recommended_message : String = "",
    @SerializedName("installment_amount") val installment_amount : String = "",
    @SerializedName("total_amount") val total_amount : String = "",
    @SerializedName("payment_method_option_id") val payment_method_option_id : String = ""
)