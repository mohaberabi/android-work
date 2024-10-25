package com.mohaberabi.androidworkmanager.domain.usecase

import com.mohaberabi.androidworkmanager.domain.repository.QuoteRepository
import javax.inject.Inject

class GetAllQuotesUseCase @Inject constructor(
    private val repository: QuoteRepository
) {


    operator fun invoke() = repository.getAllQuotes()
}