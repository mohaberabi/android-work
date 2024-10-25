package com.mohaberabi.androidworkmanager.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.androidworkmanager.domain.model.Quote
import com.mohaberabi.androidworkmanager.domain.model.SyncType


@Entity("quote")
data class QuoteEntity(

    val author: String,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val quote: String,
    val syncType: String,
)

fun Quote.toQuoteEntity() =
    QuoteEntity(
        id = id,
        quote = quote,
        author = author,
        syncType = syncType.name
    )

fun QuoteEntity.toQuote() =
    Quote(
        id = id,
        quote = quote,
        author = author,
        syncType = SyncType.valueOf(syncType)
    )