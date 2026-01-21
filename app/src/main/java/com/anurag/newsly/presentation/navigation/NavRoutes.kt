package com.anurag.newsly.presentation.navigation

sealed class NavRoutes(val route: String) {

    object News : NavRoutes("news")

    object Details : NavRoutes("details/{articleId}") {
        fun createRoute(articleId: String): String {
            return "details/$articleId"
        }
    }

    object Settings : NavRoutes("settings")
}
