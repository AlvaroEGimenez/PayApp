package com.example.geopagos.di

import com.example.geopagos.api.ApiService
import com.example.geopagos.BuildConfig
import com.example.geopagos.api.NetworkApiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApiModule {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    @Provides
    fun provideSearchApi(): NetworkApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NetworkApiService::class.java)
    }

    @Provides
    fun providePaymentMethod() : ApiService {
        return ApiService()
    }


}