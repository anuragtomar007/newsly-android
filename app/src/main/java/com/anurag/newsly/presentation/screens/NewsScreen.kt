@file:OptIn(ExperimentalMaterial3Api::class)

package com.anurag.newsly.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anurag.newsly.presentation.navigation.NavRoutes
import com.anurag.newsly.presentation.state.NewsEvent
import com.anurag.newsly.presentation.state.NewsIntent
import com.anurag.newsly.presentation.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    navController: NavHostController,
    viewModel: NewsViewModel
) {
    val state by viewModel.state.collectAsState()

    // Load data once
    LaunchedEffect(Unit) {
        viewModel.processIntent(NewsIntent.LoadNews)
    }

    // Handle one-time events
    LaunchedEffect(Unit) {
        viewModel.event.collect { event ->
            when (event) {
                is NewsEvent.NavigateToDetails -> {
                    navController.navigate(
                        NavRoutes.Details.createRoute(event.articleId)
                    )
                }

                NewsEvent.NavigateToSettings -> {
                    navController.navigate(NavRoutes.Settings.route)
                }

                is NewsEvent.ShowToast -> {
                    // Later Snackbar
                }
            }
        }
    }

    // MAIN UI
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
                        modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
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
                        modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
                    )
                }
            }
        }
    }
}
