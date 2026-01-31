package com.anurag.newsly.domain.usecase

import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.repository.NewsRepository
import com.anurag.newsly.domain.util.NetworkResult

class GetHeadlinesUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(pages: Int): NetworkResult<List<Article>> {
        return repository.getTopHeadlines(
            country = "us",
            page = 1
        )
    }
}
