package com.mohaberabi.androidworkmanager.domain.source.local

import com.mohaberabi.androidworkmanager.domain.model.Quote
import kotlinx.coroutines.flow.Flow


interface QuoteLocalDataSource {
    fun getAllQuotes(): Flow<List<Quote>>
    suspend fun saveQuote(quote: Quote)

}