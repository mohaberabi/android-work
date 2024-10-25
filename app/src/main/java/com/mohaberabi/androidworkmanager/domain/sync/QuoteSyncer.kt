package com.mohaberabi.androidworkmanager.domain.sync

import com.mohaberabi.androidworkmanager.domain.model.SyncType


interface QuoteSyncer {
    fun enqueue(type: SyncType)
}