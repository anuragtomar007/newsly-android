package com.anurag.newsly.domain.usecase

import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.repository.NewsRepository

class GetHeadlinesUseCase(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(): List<Article> {
        return repository.getHeadlines()
    }
}
