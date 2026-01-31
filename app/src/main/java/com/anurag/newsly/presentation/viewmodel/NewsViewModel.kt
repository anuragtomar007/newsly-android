package com.anurag.newsly.presentation.viewmodel

import com.anurag.newsly.domain.usecase.GetHeadlinesUseCase
import com.anurag.newsly.presentation.state.NewsEvent
import com.anurag.newsly.presentation.state.NewsIntent
import com.anurag.newsly.presentation.state.NewsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anurag.newsly.domain.util.NetworkResult
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getHeadlinesUseCase: GetHeadlinesUseCase
) : ViewModel() {

    private var currentPage = 1
    private var isLastPage = false
    private var isLoadingMore = false

    // STATE
    private val _state = MutableStateFlow(NewsState())
    val state: StateFlow<NewsState> = _state.asStateFlow()

    // EVENT
    private val _event = MutableSharedFlow<NewsEvent>()
    val event = _event.asSharedFlow()

    init {
        processIntent(NewsIntent.LoadNews)
    }

    // INTENT ENTRY POINT
    fun processIntent(intent: NewsIntent) {
        when (intent) {

            NewsIntent.LoadNews -> {
                loadNews()
            }

            is NewsIntent.OnArticleClick -> {
                emitNavigateToDetails(intent.articleId)
            }

            NewsIntent.OnSettingsClick -> {
                emitNavigateToSettings()
            }

            NewsIntent.LoadMore -> {
                fetchNews(reset = false)
            }
        }
    }

    private fun emitNavigateToDetails(articleId: String) {
        viewModelScope.launch {
            _event.emit(NewsEvent.NavigateToDetails(articleId))
        }
    }

    private fun emitNavigateToSettings() {
        viewModelScope.launch {
            _event.emit(NewsEvent.NavigateToSettings)
        }
    }

    private fun loadNews() {
        currentPage = 1
        fetchNews(reset = true)
    }

    private fun fetchNews(reset: Boolean) {
        if (isLoadingMore || isLastPage) return

        viewModelScope.launch {

            isLoadingMore = true

            val result = getHeadlinesUseCase(currentPage)

            when(result) {

                is NetworkResult.Success -> {

                    val newArticles = result.data

                    if (newArticles.isEmpty()) {
                        isLastPage = true
                    } else {
                        currentPage++
                    }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            articles = if (reset) newArticles
                            else it.articles + newArticles
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _event.emit(NewsEvent.ShowToast("Error loading news"))
                }
            }

            isLoadingMore = false
        }
    }


    /*private fun loadNews() {
        viewModelScope.launch {

            _state.update { it.copy(isLoading = true) }

            when (val result = getHeadlinesUseCase()) {

                is NetworkResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            articles = result.data,
                            error = null
                        )
                    }
                }

                is NetworkResult.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.message
                        )
                    }

                    _event.emit(
                        NewsEvent.ShowToast(result.message)
                    )
                }
            }
        }
    }*/

}
