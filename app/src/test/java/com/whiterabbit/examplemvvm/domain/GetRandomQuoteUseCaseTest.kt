package com.whiterabbit.examplemvvm.domain

import com.whiterabbit.examplemvvm.data.QuoteRepository
import com.whiterabbit.examplemvvm.domain.model.Quote
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetRandomQuoteUseCaseTest {

    @RelaxedMockK
    private lateinit var repository: QuoteRepository

    lateinit var getRandomQuoteUseCase: GetRandomQuoteUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        getRandomQuoteUseCase = GetRandomQuoteUseCase(repository)
    }

    @Test
    fun `when database is empty then return null`() = runBlocking {
        // Given
        coEvery { repository.getAllQuotesFromDatabase() } returns emptyList()

        // When
        val response = getRandomQuoteUseCase()

        // Then
        assert(response == null)
    }

    @Test
    fun `when database isn't empty then return quote`() = runBlocking {
        // Given
        val quoteList = listOf(Quote(quote = "Hello World!", author = "JPQ"))
        coEvery { repository.getAllQuotesFromDatabase() } returns quoteList

        // When
        val response = getRandomQuoteUseCase()

        // Then
        assert(response == quoteList.first())
    }
}
