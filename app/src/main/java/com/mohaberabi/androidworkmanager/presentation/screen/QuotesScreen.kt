package com.mohaberabi.androidworkmanager.presentation.screen

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mohaberabi.androidworkmanager.common.utils.formatTimestampToDMY
import com.mohaberabi.androidworkmanager.domain.model.Quote
import com.mohaberabi.androidworkmanager.presentation.viewmodel.QuoteViewModel


@Composable
fun QuotesScreenRoot(
    viewModel: QuoteViewModel = hiltViewModel(),
) {


    if (Build.VERSION.SDK_INT >= 33) {
        val launcher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
            ) {
            }
        LaunchedEffect(key1 = Unit) {
            launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }


    val quotes by viewModel.state.collectAsState()
    QuotesScreen(
        onRefresh = { viewModel.syncOneTime() },
        quotes = quotes
    )
}


@Composable
fun QuotesScreen(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit,
    quotes: List<Quote>
) {


    Scaffold { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            Button(
                onClick = {
                    onRefresh()
                },
            ) {
                Text(text = "Manually fetch one quote")
            }
            if (quotes.isEmpty()) {
                Text(text = "Please wait the worker now will fetch more and more ")
            } else {
                LazyColumn {

                    items(quotes) { quote ->
                        Card() {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = quote.quote,
                                )


                                Text(
                                    text = "Fetched at :${formatTimestampToDMY(quote.time)}",
                                )
                                Text(
                                    text = "Fetched type :${quote.syncType}",
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End,
                                ) {


                                    Text(
                                        text = quote.author,
                                    )

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}