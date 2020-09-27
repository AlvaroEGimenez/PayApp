package com.example.geopagos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.geopagos.api.ApiService
import com.example.geopagos.model.PaymentsMethod
import com.example.geopagos.ui.bank.viewmodel.PaymentMethodViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import java.util.concurrent.TimeUnit

class PaymentMethodViewModelTest {

    @get: Rule
    var rule = InstantTaskExecutorRule()

    @get: Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: ApiService

    @InjectMocks
    var paymentMethodViewModel =
        PaymentMethodViewModel()

    private var testSingle : Single<ArrayList<PaymentsMethod>>? = null


    @Test
    fun successResponse(){
        val paymentsMethod = PaymentsMethod()
        val list = arrayListOf(paymentsMethod)
        val expected = 1

        testSingle = Single.just(list)

        `when`(apiService.getPaymentMethod()).thenReturn(testSingle)
        paymentMethodViewModel.getPaymentMethod()

        Assert.assertEquals(expected,paymentMethodViewModel.paymentsMethod.value?.size)
        Assert.assertEquals(false,paymentMethodViewModel.loading.value)
        Assert.assertEquals(null, paymentMethodViewModel.error.value)

    }

    @Test
    fun errorResponse(){
        testSingle = Single.error(Throwable())

        `when`(apiService.getPaymentMethod()).thenReturn(testSingle)
        paymentMethodViewModel.getPaymentMethod()

        Assert.assertEquals(true,paymentMethodViewModel.error.value)
        Assert.assertEquals(false,paymentMethodViewModel.loading.value)
        Assert.assertEquals(null, paymentMethodViewModel.paymentsMethod.value)
    }



    @Before
    fun setup(){
        setUpRxSchedulers()
    }




    private fun setUpRxSchedulers() {
        val immediate = object : Scheduler() {
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker { it.run() }
            }
        }


        RxJavaPlugins.setInitIoSchedulerHandler {immediate}
        RxJavaPlugins.setInitComputationSchedulerHandler {immediate}
        RxJavaPlugins.setInitNewThreadSchedulerHandler {immediate}
        RxJavaPlugins.setInitSingleSchedulerHandler {immediate}
        RxAndroidPlugins.setInitMainThreadSchedulerHandler {immediate}
    }
}