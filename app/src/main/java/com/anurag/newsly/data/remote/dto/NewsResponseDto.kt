package com.anurag.newsly.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponseDto(
    val status: String?,
    val totalResults: Int?,
    val articles: List<ArticleDto>?
)
