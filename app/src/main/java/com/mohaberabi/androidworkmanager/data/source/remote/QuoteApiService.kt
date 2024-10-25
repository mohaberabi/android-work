package com.mohaberabi.androidworkmanager.data.source.remote

import com.mohaberabi.androidworkmanager.data.source.remote.dto.QuoteDto
import retrofit2.http.GET

interface QuoteApiService {
    @GET("quotes/random")
    suspend fun getQuote(): QuoteDto
}