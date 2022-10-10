package com.whiterabbit.examplemvvm.domain.model

import com.whiterabbit.examplemvvm.data.database.entity.QuoteEntity
import com.whiterabbit.examplemvvm.data.model.QuoteModel

data class Quote(val quote: String, val author: String)

fun QuoteModel.toDomain() = Quote(quote, author)
fun QuoteEntity.toDomain() = Quote(quote, author)
