package com.anurag.newsly.presentation.state

sealed interface NewsIntent {
    object LoadNews : NewsIntent
    data class OnArticleClick(val articleId: String) : NewsIntent
    object OnSettingsClick : NewsIntent
}

