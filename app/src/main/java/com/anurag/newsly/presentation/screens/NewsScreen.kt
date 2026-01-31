@file:OptIn(ExperimentalMaterial3Api::class)

package com.anurag.newsly.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
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

    val listState = rememberLazyListState()

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
                    // later snack bar
                }
            }
        }
    }

    LaunchedEffect(listState) {

        snapshotFlow {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index
        }.collect { lastVisibleIndex ->

            val totalItems = state.articles.size

            // Trigger when 3 items before end
            if (lastVisibleIndex != null &&
                lastVisibleIndex >= totalItems - 3 &&
                !state.isLoadingMore &&
                !state.isLoading
            ) {
                viewModel.processIntent(NewsIntent.LoadMore)
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
                        state = listState,
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
                                Column(
                                    modifier = Modifier.padding(16.dp)
                                ) {
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

                        if (state.isLoadingMore) {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
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
