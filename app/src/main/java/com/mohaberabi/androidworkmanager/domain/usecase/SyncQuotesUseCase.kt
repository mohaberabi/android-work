package com.mohaberabi.androidworkmanager.domain.usecase

import com.mohaberabi.androidworkmanager.domain.model.SyncType
import com.mohaberabi.androidworkmanager.domain.repository.QuoteRepository
import javax.inject.Inject

class SyncQuotesUseCase @Inject constructor(

    private val repository: QuoteRepository
) {

    operator fun invoke(type: SyncType) = repository.syncQuotes(type)
}