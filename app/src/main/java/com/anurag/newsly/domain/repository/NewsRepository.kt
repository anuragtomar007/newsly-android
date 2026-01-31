package com.anurag.newsly.domain.repository

import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.util.NetworkResult
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): NetworkResult<List<Article>>

    fun getArticleByUrl(url: String): Flow<Article?>
}
