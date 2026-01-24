package com.anurag.newsly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anurag.newsly.presentation.screens.DetailsScreen
import com.anurag.newsly.presentation.screens.NewsScreen
import com.anurag.newsly.presentation.screens.SettingsScreen
import com.anurag.newsly.presentation.viewmodel.DetailsViewModel
import com.anurag.newsly.presentation.viewmodel.NewsViewModel
import com.anurag.newsly.presentation.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NewsNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.News.route
    ) {

        // NEWS SCREEN
        composable(NavRoutes.News.route) {
            val newsViewModel: NewsViewModel = koinViewModel()
            NewsScreen(
                viewModel = newsViewModel,
                onNavigateToDetails = { articleId ->
                    navController.navigate(
                        NavRoutes.Details.createRoute(articleId)
                    ){
                        launchSingleTop = true
                    }
                },
                onNavigateToSettings = {
                    navController.navigate(NavRoutes.Settings.route){
                        launchSingleTop = true
                    }
                }
            )
        }

        // DETAILS SCREEN
        composable(
            route = NavRoutes.Details.route,
            arguments = listOf(
                navArgument("articleId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val articleId =
                backStackEntry.arguments?.getString("articleId") ?: ""

            val viewModel: DetailsViewModel = koinViewModel(
                parameters = { parametersOf(articleId) }
            )

            DetailsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        // SETTINGS SCREEN
        composable(NavRoutes.Settings.route) {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            SettingsScreen(
                viewModel = settingsViewModel,
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
