package com.mohaberabi.androidworkmanager.domain.repository

import com.mohaberabi.androidworkmanager.domain.model.Quote
import com.mohaberabi.androidworkmanager.domain.model.SyncType
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {


    fun syncQuotes(
        type: SyncType,
    )

    fun getAllQuotes(): Flow<List<Quote>>
}