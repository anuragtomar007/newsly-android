package com.anurag.newsly.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class SettingsRepository(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val FONT_SIZE_KEY = booleanPreferencesKey("font_size_large")
        val BOOKMARKS_KEY = stringSetPreferencesKey("bookmarks")
    }

    val darkModeFlow: Flow<Boolean> = context.dataStore.data
        .map { it[DARK_MODE_KEY] ?: false }

    suspend fun saveDarkMode(enabled: Boolean) {
        context.dataStore.edit { it[DARK_MODE_KEY] = enabled }
    }

    val fontSizeFlow: Flow<Boolean> = context.dataStore.data
        .map { it[FONT_SIZE_KEY] ?: false }

    suspend fun saveFontSize(isLarge: Boolean) {
        context.dataStore.edit { it[FONT_SIZE_KEY] = isLarge }
    }

    val bookmarksFlow: Flow<Set<String>> = context.dataStore.data
        .map { it[BOOKMARKS_KEY] ?: emptySet() }

    suspend fun addBookmark(articleId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[BOOKMARKS_KEY] ?: emptySet()
            prefs[BOOKMARKS_KEY] = current + articleId
        }
    }

    suspend fun removeBookmark(articleId: String) {
        context.dataStore.edit { prefs ->
            val current = prefs[BOOKMARKS_KEY] ?: emptySet()
            prefs[BOOKMARKS_KEY] = current - articleId
        }
    }
}
