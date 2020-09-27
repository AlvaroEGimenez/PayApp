package com.example.geopagos.api

import com.example.geopagos.BuildConfig
import com.example.geopagos.di.DaggerApiComponent
import com.example.geopagos.model.CardIssuers
import com.example.geopagos.model.Installments
import com.example.geopagos.model.PaymentsMethod
import io.reactivex.Single
import javax.inject.Inject

class ApiService {

    @Inject
    lateinit var api: NetworkApiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    fun getPaymentMethod(): Single<ArrayList<PaymentsMethod>> {
        return api.paymentMethods(BuildConfig.TOKEN)
    }

    fun getCardIsssuers(id: String): Single<ArrayList<CardIssuers>> {
        return api.cardIssuers(BuildConfig.KEY, id)
    }

    fun getInstallments(amount: String, bin: String, issuerId: String): Single<ArrayList<Installments>> {
        return api.installments(BuildConfig.KEY, amount, bin, issuerId)
    }
}