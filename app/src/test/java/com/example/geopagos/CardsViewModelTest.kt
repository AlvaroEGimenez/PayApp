package com.example.geopagos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.geopagos.model.CardIssuers
import com.example.geopagos.viewmodel.CardsViewModel
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

class CardsViewModelTest {
    @get: Rule
    var rule = InstantTaskExecutorRule()

    @get: Rule
    val mockito : MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: ApiService

    @InjectMocks
    var cardsViewModel = CardsViewModel()

    private var testSingle : Single<ArrayList<CardIssuers>>? = null

    @Before
    fun setup(){
        setUpRxSchedulers()
    }


    @Test
    fun successResponse() {
        val cardIssuers = CardIssuers(0, "", "", "", "", "")
        val list = arrayListOf(cardIssuers)

        testSingle = Single.just(list)
        `when`(apiService.getCardIsssuers("1")).thenReturn((testSingle))
        cardsViewModel.getCardIsuuers("1")

        Assert.assertEquals(1,cardsViewModel.cards.value?.size)
        Assert.assertEquals(null,cardsViewModel.error.value)
        Assert.assertEquals(false,cardsViewModel.loading.value)
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