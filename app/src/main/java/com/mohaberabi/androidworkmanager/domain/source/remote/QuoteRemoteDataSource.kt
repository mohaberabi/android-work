package com.mohaberabi.androidworkmanager.domain.source.remote

import com.mohaberabi.androidworkmanager.domain.model.Quote


interface QuoteRemoteDataSource {


    suspend fun getQuote(): Quote
}