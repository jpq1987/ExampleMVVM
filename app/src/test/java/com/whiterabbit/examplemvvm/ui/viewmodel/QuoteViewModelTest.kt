package com.whiterabbit.examplemvvm.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.whiterabbit.examplemvvm.domain.GetQuotesUseCase
import com.whiterabbit.examplemvvm.domain.GetRandomQuoteUseCase
import com.whiterabbit.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class QuoteViewModelTest {

    @RelaxedMockK
    private lateinit var getQuoteUseCase: GetQuotesUseCase

    @RelaxedMockK
    private lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    private lateinit var quoteViewModel: QuoteViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        quoteViewModel = QuoteViewModel(getQuoteUseCase, getRandomQuoteUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model is created ath the first time, get all quotes and set the first value`() = runTest {
        // Given
        val quoteList = listOf(Quote("Hello World!", author = "JPQ"), Quote("Hola Mundo!", author = "JPQ"))
        coEvery { getQuoteUseCase() } returns quoteList

        // When
        quoteViewModel.onCreate()

        // Then
        assert(quoteViewModel.quoteModel.value == quoteList.first())
    }

    @Test
    fun `when randomQuoteUseCase return a quote, set on livedata`() = runTest {
        // Given
        val quote = Quote("Hello World!", "JPQ")
        coEvery { getRandomQuoteUseCase() } returns quote

        // When
        quoteViewModel.randomQuote()

        // Then
        assert(quoteViewModel.quoteModel.value == quote)
    }

    @Test
    fun `when randomQuoteUseCase return null, keep the last value`() = runTest {
        // Given
        val quote = Quote("Hello World!", "JPQ")
        quoteViewModel.quoteModel.value = quote
        coEvery { getRandomQuoteUseCase() } returns null

        // When
        quoteViewModel.randomQuote()

        // Then
        assert(quoteViewModel.quoteModel.value == quote)
    }
}
