package com.anurag.newsly.presentation.state

sealed interface NewsEvent {
    data class ShowToast(val message: String) : NewsEvent
    data class NavigateToDetails(val articleId: String) : NewsEvent
    object NavigateToSettings : NewsEvent
}
