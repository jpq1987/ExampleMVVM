package com.whiterabbit.examplemvvm.domain

import com.whiterabbit.examplemvvm.data.QuoteRepository
import com.whiterabbit.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetQuotesUseCaseTest {

    @RelaxedMockK
    private lateinit var quoteRepository: QuoteRepository

    lateinit var getQuotesUseCase: GetQuotesUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getQuotesUseCase = GetQuotesUseCase(quoteRepository)
    }

    @Test
    fun `when the api doesn't return anything then get values from database`() = runBlocking {
        // Given
        coEvery { quoteRepository.getAllQuotesFromApi() } returns emptyList()

        // When
        getQuotesUseCase()

        // Then
        coVerify(exactly = 0) { quoteRepository.clearQuotes() }
        coVerify(exactly = 0) { quoteRepository.insertQuotes(any()) }
        coVerify(exactly = 1) { quoteRepository.getAllQuotesFromDatabase() }
    }

    @Test
    fun `when the api return something then get values from api`() = runBlocking {
        // Given
        val quotes = listOf(Quote(quote = "Hello World!", author = "JPQ"))
        coEvery { quoteRepository.getAllQuotesFromApi() } returns quotes

        // When
        val response = getQuotesUseCase()

        // Then
        coVerify(exactly = 1) { quoteRepository.clearQuotes() }
        coVerify(exactly = 1) { quoteRepository.insertQuotes(any()) }
        coVerify(exactly = 0) { quoteRepository.getAllQuotesFromDatabase() }
        assert(response == quotes)
    }
}
