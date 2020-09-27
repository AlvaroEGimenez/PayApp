package com.example.geopagos.ui.card.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.geopagos.api.ApiService
import com.example.geopagos.di.DaggerApiComponent
import com.example.geopagos.model.CardIssuers
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CardsViewModel : ViewModel() {

    @Inject
    lateinit var networkApiService: ApiService

    init {
        DaggerApiComponent.create().inject(this)
    }


    private val disposable = CompositeDisposable()
    val cards = MutableLiveData<ArrayList<CardIssuers>>()
    val error = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun getCardIsuuers(id : String){
        cardIsuuers(id)
    }

    private fun cardIsuuers(id: String){
        loading.value = true
        disposable.add(networkApiService.getCardIsssuers(id)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<ArrayList<CardIssuers>>(){
                override fun onSuccess(value: ArrayList<CardIssuers>?) {
                    cards.value = value
                    loading.value = false
                }
                override fun onError(e: Throwable?) {
                    error.value = true
                    loading.value = false
                }

            })
        )
    }

}