package com.anurag.newsly.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(
    val title: String?,
    val description: String?,
    val content: String?,
    val author: String?,
    val url: String?,
    @SerialName("urlToImage")
    val urlToImage: String?,
    val publishedAt: String?
)
