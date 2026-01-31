package com.anurag.newsly.data.remote.api

import com.anurag.newsly.data.remote.NetworkConstants.API_KEY
import com.anurag.newsly.data.remote.dto.NewsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class NewsApiImpl(
    private val client: HttpClient
) : NewsApi {

    override suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): NewsResponseDto {

        return client.get("v2/top-headlines") {
            parameter("country", country)
            parameter("page", page)
            parameter("apiKey", API_KEY)
        }.body()
    }
}

