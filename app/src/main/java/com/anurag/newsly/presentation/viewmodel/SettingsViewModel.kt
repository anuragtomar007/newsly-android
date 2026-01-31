package com.anurag.newsly.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anurag.newsly.data.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    val darkMode: StateFlow<Boolean> = repository.darkModeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    val fontSizeLarge: StateFlow<Boolean> = repository.fontSizeFlow
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun toggleDarkMode() {
        viewModelScope.launch {
            repository.saveDarkMode(!darkMode.value)
        }
    }

    fun toggleFontSize() {
        viewModelScope.launch {
            repository.saveFontSize(!fontSizeLarge.value)
        }
    }
}