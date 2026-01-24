package com.anurag.newsly.presentation.viewmodel

import androidx.lifecycle.ViewModel

class DetailsViewModel(
    private val articleId: String
) : ViewModel() {

    init {
        // later: load article using id
    }
}