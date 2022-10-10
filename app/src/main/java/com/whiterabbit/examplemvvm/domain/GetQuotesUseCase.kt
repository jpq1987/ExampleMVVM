package com.whiterabbit.examplemvvm.domain

import com.whiterabbit.examplemvvm.data.QuoteRepository
import com.whiterabbit.examplemvvm.data.model.QuoteModel
import javax.inject.Inject

class GetQuotesUseCase @Inject constructor(private val repository: QuoteRepository) {

    suspend operator fun invoke(): List<QuoteModel> = repository.getAllQuotes()
}
