package com.anurag.newsly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.anurag.newsly.data.repository.SettingsRepository
import com.anurag.newsly.presentation.navigation.NewsNavGraph
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val settingsRepository: SettingsRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val darkMode by settingsRepository.darkModeFlow.collectAsState(initial = false)
            val largeFont by settingsRepository.fontSizeFlow.collectAsState(initial = false)

            val typography = Typography(
                bodyMedium = TextStyle(fontSize = if (largeFont) 20.sp else 16.sp),
                titleMedium = TextStyle(fontSize = if (largeFont) 22.sp else 18.sp),
                headlineSmall = TextStyle(fontSize = if (largeFont) 24.sp else 20.sp)
            )

            MaterialTheme(
                colorScheme = if (darkMode) darkColorScheme() else lightColorScheme(),
                typography = typography
            ) {
                val navController = rememberNavController()
                NewsNavGraph(navController = navController)
            }
        }
    }
}