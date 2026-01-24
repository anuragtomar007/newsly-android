package com.anurag.newsly.di

import com.anurag.newsly.data.repository.NewsRepositoryImpl
import com.anurag.newsly.domain.repository.NewsRepository
import com.anurag.newsly.domain.usecase.GetHeadlinesUseCase
import com.anurag.newsly.presentation.viewmodel.DetailsViewModel
import com.anurag.newsly.presentation.viewmodel.NewsViewModel
import com.anurag.newsly.presentation.viewmodel.SettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<NewsRepository> {
        NewsRepositoryImpl()
    }

    single {
        GetHeadlinesUseCase(
            repository = get()
        )
    }

    viewModel {
        NewsViewModel(
            getHeadlinesUseCase = get()
        )
    }

    viewModel { (articleId: String) ->
        DetailsViewModel(articleId)
    }

    viewModel { SettingsViewModel() }
}