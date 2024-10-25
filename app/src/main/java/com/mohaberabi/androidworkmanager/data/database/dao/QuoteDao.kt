package com.mohaberabi.androidworkmanager.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mohaberabi.androidworkmanager.data.database.entity.QuoteEntity
import kotlinx.coroutines.flow.Flow


@Dao

interface QuoteDao {


    @Query("SELECT * FROM quote")
    fun getAllQuotes(): Flow<List<QuoteEntity>>

    @Upsert
    suspend fun addQuote(quote: QuoteEntity)
}