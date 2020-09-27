package com.example.geopagos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.geopagos.api.ApiService
import com.example.geopagos.model.Installments
import com.example.geopagos.viewmodel.InstallmentsViewModel
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
import java.util.concurrent.TimeUnit

class InstallmentsViewModelTest {
    @get: Rule
    var rule = InstantTaskExecutorRule()

    @get: Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: ApiService

    @InjectMocks
    var installmentsViewModel = InstallmentsViewModel()

    private var testSingle : Single<ArrayList<Installments>>? = null

    @Before
    fun setup(){
        setUpRxSchedulers()
    }

    @Test
    fun successResponse(){
        val installments = Installments()
        val list = arrayListOf(installments)

        testSingle = Single.just(list)

        `when`(apiService.getInstallments("","","")).thenReturn(testSingle)
        apiService.getInstallments("","","")

        Assert.assertEquals(1,installmentsViewModel.data.value?.size)
        Assert.assertEquals(false,installmentsViewModel.loaging.value)
        Assert.assertEquals(null,installmentsViewModel.error.value)
    }

    @Test
    fun errorResponse(){
        testSingle = Single.error(Throwable())

        `when`(apiService.getInstallments("","","")).thenReturn(testSingle)

        installmentsViewModel.getInstallments("","","")
        Assert.assertEquals(true,installmentsViewModel.error.value)
        Assert.assertEquals(false,installmentsViewModel.loaging.value)
        Assert.assertEquals(null,installmentsViewModel.data.value)
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