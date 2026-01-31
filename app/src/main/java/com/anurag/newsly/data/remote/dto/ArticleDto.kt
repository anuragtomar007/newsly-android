package com.anurag.newsly.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ArticleDto(

    val title: String?,
    val description: String?,
    val author: String?,
    @SerialName("urlToImage")
    val imageUrl: String?
)
