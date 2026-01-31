package com.anurag.newsly.data.remote.mapper

import com.anurag.newsly.data.remote.dto.ArticleDto
import com.anurag.newsly.domain.model.Article

object NewsMapper {

    fun ArticleDto.toDomain(): Article {
        return Article(
            id = (title ?: "") + (author ?: "") + (imageUrl ?: ""), // simple unique id
            title = title ?: "",
            description = description ?: "",
            author = author ?: "Unknown",
            imageUrl = imageUrl ?: ""
        )
    }
}

