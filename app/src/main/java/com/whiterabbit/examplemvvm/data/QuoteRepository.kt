package com.whiterabbit.examplemvvm.data

import com.whiterabbit.examplemvvm.data.model.QuoteModel
import com.whiterabbit.examplemvvm.data.model.QuoteProvider
import com.whiterabbit.examplemvvm.data.network.QuoteService

class QuoteRepository {

    private val api = QuoteService()

    suspend fun getAllQuotes(): List<QuoteModel> {
        val response = api.getQuotes()
        QuoteProvider.quotes = response
        return response
    }
}