package com.anurag.newsly.data.repository

import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.repository.NewsRepository

class NewsRepositoryImpl : NewsRepository {

    override suspend fun getHeadlines(): List<Article> {
        return listOf(
            Article(
                id = "1",
                title = "Breaking News",
                description = "Something happened"
            ),
            Article(
                id = "2",
                title = "Android",
                description = "MVI is powerful"
            )
        )
    }
}
