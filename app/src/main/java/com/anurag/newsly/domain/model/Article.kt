package com.anurag.newsly.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val title: String?,
    val description: String?,
    val content: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?
)
