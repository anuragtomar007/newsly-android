package com.anurag.newsly.domain.model

data class Article(
    val id: String,
    val title: String,
    val description: String,
    val author: String,
    val imageUrl: String
)
