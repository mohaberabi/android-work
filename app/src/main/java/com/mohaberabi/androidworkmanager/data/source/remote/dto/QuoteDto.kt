package com.mohaberabi.androidworkmanager.data.source.remote.dto

import com.mohaberabi.androidworkmanager.domain.model.Quote

data class QuoteDto(
    val author: String,
    val id: Int,
    val quote: String
)

fun QuoteDto.toQuote() = Quote(
    author = author,
    id = id,
    quote = quote
)