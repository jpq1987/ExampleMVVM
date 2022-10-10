package com.whiterabbit.examplemvvm.data

import com.whiterabbit.examplemvvm.data.model.QuoteModel
import com.whiterabbit.examplemvvm.data.model.QuoteProvider
import com.whiterabbit.examplemvvm.data.network.QuoteService
import javax.inject.Inject

class QuoteRepository @Inject constructor(
    private val api: QuoteService,
    private val quoteProvider: QuoteProvider
) {

    suspend fun getAllQuotes(): List<QuoteModel> {
        val response = api.getQuotes()
        quoteProvider.quotes = response
        return response
    }
}
