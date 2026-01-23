package com.anurag.newsly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.anurag.newsly.data.repository.NewsRepositoryImpl
import com.anurag.newsly.domain.usecase.GetHeadlinesUseCase
import com.anurag.newsly.presentation.navigation.NewsNavGraph
import com.anurag.newsly.presentation.viewmodel.NewsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = NewsRepositoryImpl()
        val useCase = GetHeadlinesUseCase(repository)

        setContent {
            val navController = rememberNavController()
            val viewModel: NewsViewModel = viewModel(
                factory = object : ViewModelProvider.Factory {
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return NewsViewModel(useCase) as T
                    }
                }
            )

            NewsNavGraph(
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}