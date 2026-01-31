package com.anurag.newsly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anurag.newsly.data.repository.SettingsRepository
import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.repository.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val newsRepository: NewsRepository,
    private val settingsRepository: SettingsRepository,
    private val articleId: String
) : ViewModel() {

    val article: StateFlow<Article?> =
        newsRepository.getArticleByUrl(articleId)
            .stateIn(viewModelScope, SharingStarted.Eagerly, null)

    val bookmarks: StateFlow<Set<String>> =
        settingsRepository.bookmarksFlow
            .stateIn(viewModelScope, SharingStarted.Eagerly, emptySet())

    fun addBookmark() {
        viewModelScope.launch {
            settingsRepository.addBookmark(articleId)
        }
    }

    fun removeBookmark() {
        viewModelScope.launch {
            settingsRepository.removeBookmark(articleId)
        }
    }

    fun isBookmarked(): Boolean {
        return bookmarks.value.contains(articleId)
    }
}