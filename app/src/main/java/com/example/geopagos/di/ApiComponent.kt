package com.example.geopagos.di

import com.example.geopagos.api.ApiService
import com.example.geopagos.ui.bank.viewmodel.PaymentMethodViewModel
import com.example.geopagos.ui.card.viewmodel.CardsViewModel
import com.example.geopagos.ui.resume.viewmodel.InstallmentsViewModel
import dagger.Component

@Component(modules = [ApiModule::class])
interface ApiComponent {
    fun inject (service: ApiService)

    fun inject (viewModel: PaymentMethodViewModel)

    fun inject (cardsViewModel: CardsViewModel)

    fun inject (installmentsViewModel: InstallmentsViewModel)

}