package com.anurag.newsly.presentation.navigation

import android.net.Uri

sealed class NavRoutes(val route: String) {

    object News : NavRoutes("news")

    object Details : NavRoutes("details/{articleUrl}") {
        fun createRoute(articleUrl: String): String {
            return "details/${Uri.encode(articleUrl)}"
        }
    }

    object Settings : NavRoutes("settings")
}
