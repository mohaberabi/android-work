package com.mohaberabi.androidworkmanager.data.source.local

import com.mohaberabi.androidworkmanager.data.database.dao.QuoteDao
import com.mohaberabi.androidworkmanager.data.database.entity.toQuote
import com.mohaberabi.androidworkmanager.data.database.entity.toQuoteEntity
import com.mohaberabi.androidworkmanager.domain.model.Quote
import com.mohaberabi.androidworkmanager.domain.source.local.QuoteLocalDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomQuoteLocalDataSource @Inject constructor(
    private val dao: QuoteDao,
) : QuoteLocalDataSource {
    override fun getAllQuotes(): Flow<List<Quote>> =
        dao.getAllQuotes().map { list -> list.map { it.toQuote() } }.flowOn(Dispatchers.IO)

    override suspend fun saveQuote(quote: Quote) = dao.addQuote(quote = quote.toQuoteEntity())
}