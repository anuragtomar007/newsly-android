package com.anurag.newsly.di

import com.anurag.newsly.BuildConfig
import com.anurag.newsly.data.remote.api.NewsApi
import com.anurag.newsly.data.remote.api.NewsApiImpl
import com.anurag.newsly.data.repository.NewsRepositoryImpl
import com.anurag.newsly.domain.repository.NewsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {

    single {
        HttpClient(Android) {

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            install(Logging) {
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 15_000
                connectTimeoutMillis = 10_000
                socketTimeoutMillis = 15_000
            }

            install(DefaultRequest) {
                url("https://newsapi.org/")
                header("X-Api-Key", BuildConfig.NEWS_API_KEY)
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 2)
                exponentialDelay()
                retryIf { _, response ->
                    response.status.value >= 500
                }
            }
        }
    }

    single<NewsApi> { NewsApiImpl(get()) }

    single<NewsRepository> { NewsRepositoryImpl(get()) }
}

