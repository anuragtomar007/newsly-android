package com.anurag.newsly.data.repository

import com.anurag.newsly.data.remote.api.NewsApi
import com.anurag.newsly.data.remote.mapper.NewsMapper.toDomain
import com.anurag.newsly.domain.model.Article
import com.anurag.newsly.domain.repository.NewsRepository
import com.anurag.newsly.domain.util.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
private val api: NewsApi
) : NewsRepository {

    private val articlesFlow = MutableStateFlow<List<Article>>(emptyList())

    override suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): NetworkResult<List<Article>> {
        return try {
            val response = api.getTopHeadlines(country, page)

            val articles = response.articles
                ?.map { it.toDomain() }
                ?: emptyList()

            articlesFlow.value = articlesFlow.value + articles

            NetworkResult.Success(articles)

        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override fun getArticleByUrl(url: String): Flow<Article?> {
        return articlesFlow.map { list ->
            list.firstOrNull { it.url == url }
        }
    }
}
