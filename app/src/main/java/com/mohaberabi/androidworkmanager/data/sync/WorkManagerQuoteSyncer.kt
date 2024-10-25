package com.mohaberabi.androidworkmanager.data.sync

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mohaberabi.androidworkmanager.data.work.OneTimeQuoteWorker
import com.mohaberabi.androidworkmanager.data.work.PeriodicQuoteWorker
import com.mohaberabi.androidworkmanager.domain.model.SyncType
import com.mohaberabi.androidworkmanager.domain.sync.QuoteSyncer
import java.time.Duration
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class WorkManagerQuoteSyncer @Inject constructor(
    private val workManager: WorkManager
) : QuoteSyncer {


    companion object {
        const val PERIODIC_UNIQUE_WORK_ID = "PERIODIC_UNIQUE_WORK_ID-QUOTES"
    }

    override fun enqueue(type: SyncType) {

        when (type) {
            SyncType.OneTime -> enqueueOneTime()
            SyncType.Periodic -> enqueuePeriodic()
        }
    }

    private val defaultConstraints by lazy {
        Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
    }

    private fun enqueueOneTime() {
        val constraints = defaultConstraints.build()
        val request = OneTimeWorkRequestBuilder<OneTimeQuoteWorker>()
            .setConstraints(constraints)
            .build()
        workManager.enqueue(request)
    }

    private fun enqueuePeriodic() {
        val constraints = defaultConstraints.build()
        val request =
            PeriodicWorkRequestBuilder<PeriodicQuoteWorker>(16, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build()
        workManager.enqueueUniquePeriodicWork(
            PERIODIC_UNIQUE_WORK_ID,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

}