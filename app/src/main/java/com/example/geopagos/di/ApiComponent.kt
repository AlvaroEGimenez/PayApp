package com.example.geopagos.di

import com.example.geopagos.api.ApiService
import com.example.geopagos.viewmodel.CardsViewModel
import com.example.geopagos.viewmodel.InstallmentsViewModel
import com.example.geopagos.viewmodel.PaymentMethodViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject (service: ApiService)

    fun inject (viewModel: PaymentMethodViewModel)

    fun inject (cardsViewModel: CardsViewModel)

    fun inject (installmentsViewModel: InstallmentsViewModel)

}