package com.whiterabbit.examplemvvm.domain

import com.whiterabbit.examplemvvm.data.QuoteRepository
import com.whiterabbit.examplemvvm.data.database.entity.toDatabase
import com.whiterabbit.examplemvvm.domain.model.Quote
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {

    suspend operator fun invoke(): List<Quote> {
        val quotes = repository.getAllQuotesFromApi()

        return if (quotes.isNotEmpty()) {
            repository.clearQuotes()
            repository.insertQuotes(quotes.map { it.toDatabase() })
            quotes
        } else {
            repository.getAllQuotesFromDatabase()
        }
    }
}
