package com.anurag.newsly.data.remote.mapper

import com.anurag.newsly.data.remote.dto.ArticleDto
import com.anurag.newsly.domain.model.Article

object NewsMapper {

    fun ArticleDto.toDomain(): Article {
        return Article(
            title = title,
            description = description,
            content = content,
            url = url,
            urlToImage = urlToImage,
            publishedAt = publishedAt
        )
    }
}

