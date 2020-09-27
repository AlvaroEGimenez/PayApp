package com.example.geopagos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.geopagos.api.ApiService
import com.example.geopagos.model.Installments
import com.example.geopagos.ui.resume.viewmodel.InstallmentsViewModel
import com.example.geopagos.utils.RxSchedulers
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

class InstallmentsViewModelTest {
    @get: Rule
    var rule = InstantTaskExecutorRule()

    @get: Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: ApiService

    @InjectMocks
    var installmentsViewModel =
        InstallmentsViewModel()

    private var testSingle: Single<ArrayList<Installments>>? = null
    private val amount = ""
    private val bin = ""
    private val issuer = ""

    @Before
    fun setup() {
        RxSchedulers.setUpRxSchedulers()
    }


    @Test
    fun successResponse() {
        val installments = Installments()
        val list = arrayListOf(installments)

        testSingle = Single.just(list)

        val expected = 1

        `when`(apiService.getInstallments(amount, bin, issuer)).thenReturn(testSingle)
        apiService.getInstallments(amount, bin, issuer)

        Assert.assertEquals(expected, installmentsViewModel.data.value?.size)
        Assert.assertEquals(false, installmentsViewModel.loaging.value)
        Assert.assertEquals(null, installmentsViewModel.error.value)
    }

    @Test
    fun errorResponse() {
        testSingle = Single.error(Throwable())

        `when`(apiService.getInstallments(amount, bin, issuer)).thenReturn(testSingle)

        installmentsViewModel.getInstallments(amount, bin, issuer)

        Assert.assertEquals(true, installmentsViewModel.error.value)
        Assert.assertEquals(false, installmentsViewModel.loaging.value)
        Assert.assertEquals(null, installmentsViewModel.data.value)
    }

}