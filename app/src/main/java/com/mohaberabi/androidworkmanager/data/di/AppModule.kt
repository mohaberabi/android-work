package com.mohaberabi.androidworkmanager.data.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.google.gson.Gson
import com.mohaberabi.androidworkmanager.data.database.AppDatabase
import com.mohaberabi.androidworkmanager.data.database.dao.QuoteDao
import com.mohaberabi.androidworkmanager.data.repository.OfflineFirstQuoteRepository
import com.mohaberabi.androidworkmanager.data.source.local.RoomQuoteLocalDataSource
import com.mohaberabi.androidworkmanager.data.source.remote.QuoteApiService
import com.mohaberabi.androidworkmanager.data.source.remote.RetrofitQuoteRemoteDataSource
import com.mohaberabi.androidworkmanager.data.sync.AndroidNotificationManagerSyncNotifier
import com.mohaberabi.androidworkmanager.data.sync.WorkManagerQuoteSyncer
import com.mohaberabi.androidworkmanager.domain.repository.QuoteRepository
import com.mohaberabi.androidworkmanager.domain.source.local.QuoteLocalDataSource
import com.mohaberabi.androidworkmanager.domain.source.remote.QuoteRemoteDataSource
import com.mohaberabi.androidworkmanager.domain.sync.QuoteSyncer
import com.mohaberabi.androidworkmanager.domain.sync.SyncNotifier
import com.mohaberabi.androidworkmanager.domain.usecase.GetAllQuotesUseCase
import com.mohaberabi.androidworkmanager.domain.usecase.SyncQuotesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context, AppDatabase::class.java,
        "room.db"
    ).build()

    @Singleton
    @Provides
    fun provideDao(
        database: AppDatabase,
    ) = database.dao()

    @Singleton
    @Provides
    fun provideWorkManager(
        @ApplicationContext context: Context,
    ) = WorkManager.getInstance(context)


    @Singleton
    @Provides
    fun provideApi(): QuoteApiService = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://dummyjson.com/")
        .build()
        .create(QuoteApiService::class.java)

    @Singleton
    @Provides
    fun provideSyncer(
        workManager: WorkManager,
    ): QuoteSyncer = WorkManagerQuoteSyncer(workManager)


    @Singleton
    @Provides
    fun provideLocalSource(
        dao: QuoteDao,
    ): QuoteLocalDataSource = RoomQuoteLocalDataSource(dao)


    @Singleton
    @Provides
    fun provideRemoteSource(
        api: QuoteApiService,
    ): QuoteRemoteDataSource =
        RetrofitQuoteRemoteDataSource(api)


    @Singleton
    @Provides
    fun provideRepos(sync: QuoteSyncer, local: QuoteLocalDataSource): QuoteRepository =
        OfflineFirstQuoteRepository(local, sync)

    @Singleton
    @Provides
    fun provideGetAllQuotes(repo: QuoteRepository) = GetAllQuotesUseCase(repo)

    @Singleton
    @Provides
    fun provideSyncUseCase(repo: QuoteRepository) = SyncQuotesUseCase(repo)


    @Singleton
    @Provides
    fun provideSyncNotifier(
        @ApplicationContext context: Context,
    ): SyncNotifier =
        AndroidNotificationManagerSyncNotifier(context)
}