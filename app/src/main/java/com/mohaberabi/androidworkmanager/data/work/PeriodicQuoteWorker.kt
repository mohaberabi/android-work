package com.mohaberabi.androidworkmanager.data.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mohaberabi.androidworkmanager.domain.model.SyncType
import com.mohaberabi.androidworkmanager.domain.source.local.QuoteLocalDataSource
import com.mohaberabi.androidworkmanager.domain.source.remote.QuoteRemoteDataSource
import com.mohaberabi.androidworkmanager.domain.sync.SyncNotifier
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@HiltWorker
class PeriodicQuoteWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val quoteRemoteDataSource: QuoteRemoteDataSource,
    private val quoteLocalDataSource: QuoteLocalDataSource,
    private val syncNotifier: SyncNotifier
) : CoroutineWorker(
    context, params
) {
    override suspend fun doWork(): Result {
        return try {
            withContext(Dispatchers.IO) {
                val quote = quoteRemoteDataSource.getQuote()
                quoteLocalDataSource.saveQuote(quote.copy(syncType = SyncType.Periodic))
                withContext(Dispatchers.Main) {
                    syncNotifier.show(quote.quote, quote.author)
                }
                Result.success()
            }
        } catch (e: Exception) {
            Result.failure()
        }
    }
}