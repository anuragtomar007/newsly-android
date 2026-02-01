package com.anurag.newsly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.anurag.newsly.presentation.screens.CoilPlaygroundScreen
import com.anurag.newsly.presentation.screens.DetailsScreen
import com.anurag.newsly.presentation.screens.NewsScreen
import com.anurag.newsly.presentation.screens.SettingsScreen
import com.anurag.newsly.presentation.screens.ToolsScreen
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

        composable(
            route = NavRoutes.Details.route,
            arguments = listOf(
                navArgument("articleUrl") { type = NavType.StringType }
            )
        ) { backStackEntry ->

            val articleUrl = backStackEntry.arguments?.getString("articleUrl") ?: return@composable
            val viewModel: DetailsViewModel = koinViewModel(
                parameters = { parametersOf(articleUrl) }
            )
            DetailsScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(NavRoutes.Settings.route) {
            val settingsViewModel: SettingsViewModel = koinViewModel()
            SettingsScreen(
                viewModel = settingsViewModel,
                onBack = {
                    navController.popBackStack()
                },
                onOpenTools = {
                    navController.navigate(NavRoutes.Tools.route)
                },
                onOpenCoil = {
                    navController.navigate(NavRoutes.CoilPlayGround.route)
                }
            )
        }

        composable(NavRoutes.Tools.route) {
            ToolsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable("coil_playground") {
            CoilPlaygroundScreen {
                navController.popBackStack()
            }
        }

    }
}
