package com.anurag.newsly.presentation.state

import com.anurag.newsly.domain.model.Article

data class NewsState(
    val isLoading: Boolean = false,
    val articles: List<Article> = emptyList(),
    val error: String? = null
)
