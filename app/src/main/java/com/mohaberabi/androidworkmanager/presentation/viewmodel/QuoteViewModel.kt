package com.mohaberabi.androidworkmanager.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mohaberabi.androidworkmanager.domain.model.Quote
import com.mohaberabi.androidworkmanager.domain.model.SyncType
import com.mohaberabi.androidworkmanager.domain.usecase.GetAllQuotesUseCase
import com.mohaberabi.androidworkmanager.domain.usecase.SyncQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val getAllQuotesUseCase: GetAllQuotesUseCase,
    private val syncQuotesUseCase: SyncQuotesUseCase
) : ViewModel() {

    val state = getAllQuotesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = listOf<Quote>()
        )


    init {
        syncPeriodically()
    }

    private fun syncPeriodically() = syncQuotesUseCase(SyncType.Periodic)
    fun syncOneTime() = syncQuotesUseCase(
        type = SyncType.OneTime,
    )
}