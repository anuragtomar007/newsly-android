@file:OptIn(ExperimentalMaterial3Api::class)

package com.anurag.newsly.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anurag.newsly.presentation.state.NewsEvent
import com.anurag.newsly.presentation.state.NewsIntent
import com.anurag.newsly.presentation.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    onNavigateToDetails: (String) -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    // Load once
    LaunchedEffect(Unit) {
        viewModel.processIntent(NewsIntent.LoadNews)
    }

    // Observe one-time events
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {

                is NewsEvent.NavigateToDetails -> {
                    onNavigateToDetails(event.articleId)
                }

                NewsEvent.NavigateToSettings -> {
                    onNavigateToSettings()
                }

                is NewsEvent.ShowToast -> {
                    // later snackbar
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Newsly") },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.processIntent(NewsIntent.OnSettingsClick)
                        }
                    ) {
                        Text("⚙️")
                    }
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            when {
                state.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.articles.isNotEmpty() -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.articles) { article ->

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.processIntent(
                                            NewsIntent.OnArticleClick(article.id)
                                        )
                                    }
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = article.title,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = article.description,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }

                else -> {
                    Text(
                        text = "No news found",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
