package com.anurag.newsly.data.remote.api

import com.anurag.newsly.data.remote.dto.NewsResponseDto

interface NewsApi {

    suspend fun getTopHeadlines(
        country: String,
        page: Int
    ): NewsResponseDto
}
