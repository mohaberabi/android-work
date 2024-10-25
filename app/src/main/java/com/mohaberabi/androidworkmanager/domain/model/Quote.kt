package com.mohaberabi.androidworkmanager.domain.model


data class Quote(
    val author: String,
    val id: Int,
    val quote: String,
    val time: Long = System.currentTimeMillis(),
    val syncType: SyncType = SyncType.OneTime
)