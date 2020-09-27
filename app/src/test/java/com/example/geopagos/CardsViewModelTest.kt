package com.example.geopagos

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.geopagos.api.ApiService
import com.example.geopagos.model.CardIssuers
import com.example.geopagos.ui.card.viewmodel.CardsViewModel
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

class CardsViewModelTest {
    @get: Rule
    var rule = InstantTaskExecutorRule()

    @get: Rule
    val mockito: MockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var apiService: ApiService

    @InjectMocks
    var cardsViewModel = CardsViewModel()

    private var testSingle: Single<ArrayList<CardIssuers>>? = null
    private val cardId = "1"

    @Before
    fun setup() {
        RxSchedulers.setUpRxSchedulers()
    }


    @Test
    fun successResponse() {
        val cardIssuers = CardIssuers()
        val list = arrayListOf(cardIssuers)
        val sizeExpected = 1

        testSingle = Single.just(list)
        `when`(apiService.getCardIsssuers(cardId)).thenReturn((testSingle))
        cardsViewModel.getCardIsuuers(cardId)

        Assert.assertEquals(sizeExpected, cardsViewModel.cards.value?.size)
        Assert.assertEquals(null, cardsViewModel.error.value)
        Assert.assertEquals(false, cardsViewModel.loading.value)
    }

    @Test
    fun errorResponse() {
        testSingle = Single.error(Throwable())

        `when`(apiService.getCardIsssuers(cardId)).thenReturn(testSingle)
        apiService.getCardIsssuers(cardId)

        Assert.assertEquals(false, cardsViewModel.loading.value)
        Assert.assertEquals(true, cardsViewModel.error.value)
        Assert.assertEquals(null, cardsViewModel.cards.value)
    }

}