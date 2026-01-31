package com.anurag.newsly.domain.repository

import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.util.NetworkResult

interface NewsRepository {

    suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): NetworkResult<List<Article>>
}
