package com.mohaberabi.androidworkmanager.data.repository

import com.mohaberabi.androidworkmanager.domain.model.Quote
import com.mohaberabi.androidworkmanager.domain.model.SyncType
import com.mohaberabi.androidworkmanager.domain.repository.QuoteRepository
import com.mohaberabi.androidworkmanager.domain.source.local.QuoteLocalDataSource
import com.mohaberabi.androidworkmanager.domain.sync.QuoteSyncer
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OfflineFirstQuoteRepository @Inject constructor(
    private val quoteLocalDataSource: QuoteLocalDataSource,
    private val quoteSyncer: QuoteSyncer
) : QuoteRepository {
    override fun syncQuotes(type: SyncType) = quoteSyncer.enqueue(type)
    override fun getAllQuotes(): Flow<List<Quote>> = quoteLocalDataSource.getAllQuotes()
}