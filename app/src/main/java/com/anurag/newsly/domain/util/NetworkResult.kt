package com.anurag.newsly.domain.util

sealed class NetworkResult<out T> {

    data class Success<T>(val data: T) : NetworkResult<T>()

    data class Error(
        val message: String
    ) : NetworkResult<Nothing>()
}
