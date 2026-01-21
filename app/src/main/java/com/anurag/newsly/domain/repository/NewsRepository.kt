package com.anurag.newsly.domain.repository

import com.anurag.newsly.domain.model.Article

interface NewsRepository {
    suspend fun getHeadlines(): List<Article>
}
