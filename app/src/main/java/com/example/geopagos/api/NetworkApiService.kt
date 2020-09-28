package com.example.geopagos.api

import com.example.geopagos.model.CardIssuers
import com.example.geopagos.model.Installments
import com.example.geopagos.model.PaymentsMethod
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NetworkApiService {
    @GET("payment_methods")
    fun paymentMethods(@Header("Authorization") token: String): Single<ArrayList<PaymentsMethod>>

    @GET("payment_methods/card_issuers")
    fun cardIssuers(
        @Query("public_key") publicKey: String,
        @Query("payment_method_id") id: String
    ): Single<ArrayList<CardIssuers>>

    @GET("payment_methods/installments")
    fun installments(
        @Query("public_key") publicKey: String,
        @Query("amount") amount: String,
        @Query("payment_method_id") bin: String,
        @Query("issuer.id") issuerId: String
    ): Single<ArrayList<Installments>>

}