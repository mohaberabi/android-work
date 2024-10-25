package com.mohaberabi.androidworkmanager.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohaberabi.androidworkmanager.data.database.dao.QuoteDao
import com.mohaberabi.androidworkmanager.data.database.entity.QuoteEntity


@Database(
    entities = [
        QuoteEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): QuoteDao
}