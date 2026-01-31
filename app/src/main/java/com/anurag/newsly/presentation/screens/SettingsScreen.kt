@file:OptIn(ExperimentalMaterial3Api::class)

package com.anurag.newsly.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.anurag.newsly.presentation.viewmodel.SettingsViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    onBack: () -> Unit
) {
    val darkMode by viewModel.darkMode.collectAsState()
    val fontSizeLarge by viewModel.fontSizeLarge.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Settings", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.padding(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Dark Mode")
                Switch(
                    checked = darkMode,
                    onCheckedChange = { viewModel.toggleDarkMode() }
                )
            }

            Spacer(modifier = Modifier.padding(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Large Font")
                Switch(
                    checked = fontSizeLarge,
                    onCheckedChange = { viewModel.toggleFontSize() }
                )
            }
        }
    }
}
