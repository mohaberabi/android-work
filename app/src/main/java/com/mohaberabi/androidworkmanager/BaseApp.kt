package com.mohaberabi.androidworkmanager

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import androidx.work.WorkManager
import androidx.work.WorkerFactory
import com.mohaberabi.androidworkmanager.domain.sync.SyncNotifier
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class BaseApp : Application() {

    @Inject
    lateinit var syncNotifier: SyncNotifier

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory
    override fun onCreate() {
        super.onCreate()
        syncNotifier.createNotificationChannels()
        WorkManager.initialize(
            this,
            Configuration.Builder()
                .setWorkerFactory(hiltWorkerFactory).build()
        )
    }


}