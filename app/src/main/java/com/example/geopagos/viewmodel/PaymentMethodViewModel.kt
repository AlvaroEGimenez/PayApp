package com.example.geopagos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geopagos.ApiService
import com.example.geopagos.model.PaymentsMethod
import com.example.geopagos.di.DaggerApiComponent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class PaymentMethodViewModel : ViewModel() {

    @Inject
    lateinit var networkApiService: ApiService

    init {
        DaggerApiComponent.create().inject(this)
    }


    private val disposable = CompositeDisposable()
    val paymentsMethod = MutableLiveData<ArrayList<PaymentsMethod>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun getPaymentMethod() {
        paymentMethods()
    }

    private fun paymentMethods() {
        loading.value = true
        disposable.add(
            networkApiService.getPaymentMethod()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<ArrayList<PaymentsMethod>>() {
                    override fun onSuccess(value: ArrayList<PaymentsMethod>?) {
                        paymentsMethod.value = value
                        loading.value = false
                    }

                    override fun onError(e: Throwable?) {
                        error.value = true
                        loading.value = false
                    }

                }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}