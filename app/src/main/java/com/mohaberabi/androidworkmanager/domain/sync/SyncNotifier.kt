package com.mohaberabi.androidworkmanager.domain.sync


interface SyncNotifier {

    fun show(
        title: String,
        body: String,
    )


    fun canShow(): Boolean

    fun createNotificationChannels()
}