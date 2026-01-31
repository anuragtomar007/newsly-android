package com.anurag.newsly.data.repository

import com.anurag.newsly.data.remote.api.NewsApi
import com.anurag.newsly.data.remote.mapper.NewsMapper.toDomain
import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.repository.NewsRepository
import com.anurag.newsly.domain.util.NetworkResult

class NewsRepositoryImpl(
private val api: NewsApi
) : NewsRepository {

    override suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): NetworkResult<List<Article>> {
        return try {
            val response = api.getTopHeadlines(country, page)

            val articles = response.articles
                ?.map { it.toDomain() }
                ?: emptyList()

            NetworkResult.Success(articles)

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}
