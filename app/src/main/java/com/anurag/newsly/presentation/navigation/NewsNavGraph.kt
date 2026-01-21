package com.anurag.newsly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anurag.newsly.presentation.screens.DetailsScreen
import com.anurag.newsly.presentation.screens.NewsScreen
import com.anurag.newsly.presentation.screens.SettingsScreen
import com.anurag.newsly.presentation.viewmodel.NewsViewModel

@Composable
fun NewsNavGraph(
    navController: NavHostController,
    viewModel: NewsViewModel
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.News.route
    ) {


        composable(NavRoutes.News.route) {
            NewsScreen(
                viewModel = viewModel,
                navController = navController
            )
        }


        composable(
            route = NavRoutes.Details.route,
            arguments = listOf(
                navArgument("articleId") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val articleId = backStackEntry.arguments?.getString("articleId")

            // Safety check
            articleId?.let {
                DetailsScreen(articleId = it)
            }
        }


        composable(NavRoutes.Settings.route) {
            SettingsScreen()
        }
    }
}
