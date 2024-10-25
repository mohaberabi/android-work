package com.mohaberabi.androidworkmanager.data.source.remote

import com.mohaberabi.androidworkmanager.data.source.remote.dto.toQuote
import com.mohaberabi.androidworkmanager.domain.model.Quote
import com.mohaberabi.androidworkmanager.domain.source.remote.QuoteRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitQuoteRemoteDataSource(
    private val api: QuoteApiService
) : QuoteRemoteDataSource {
    override suspend fun getQuote(): Quote {
        return withContext(Dispatchers.IO) {
            api.getQuote().toQuote()
        }
    }
}