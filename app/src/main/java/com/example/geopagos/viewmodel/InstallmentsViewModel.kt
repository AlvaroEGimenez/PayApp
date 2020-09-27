package com.example.geopagos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geopagos.ApiService
import com.example.geopagos.di.DaggerApiComponent
import com.example.geopagos.model.Installments
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class InstallmentsViewModel : ViewModel() {

    @Inject
    lateinit var apiService: ApiService

    init {
        DaggerApiComponent.create().inject(this)
    }

    private val composite = CompositeDisposable()
    val data = MutableLiveData<ArrayList<Installments>>()
    val loaging = MutableLiveData<Boolean>()
    val error = MutableLiveData<Boolean>()

    fun getInstallments(amount: String, bin: String, issuerId: String) {
        installments(amount, bin, issuerId)
    }

    private fun installments(amount: String, bin: String, issuerId: String) {
        loaging.value = true
        composite.add(apiService.getInstallments(amount, bin, issuerId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ArrayList<Installments>>() {
                override fun onSuccess(value: ArrayList<Installments>?) {
                    data.value = value
                    loaging.value = false
                }

                override fun onError(e: Throwable?) {
                    error.value = true
                    loaging.value = false
                }

            })
        )
    }

}