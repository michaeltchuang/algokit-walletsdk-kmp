package com.michaeltchuang.walletsdk.ui.settings.domain.theme

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.michaeltchuang.walletsdk.core.network.domain.AndroidContextHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.dataStore by preferencesDataStore(name = "theme_preferences")
private val THEME_KEY = stringPreferencesKey("theme_key")

actual class ThemePreferenceRepository actual constructor(
    context: Any?,
) {
    private val ctx = AndroidContextHolder.applicationContext

    fun themeToString(pref: ThemePreference): String = pref.name

    fun stringToTheme(str: String?): ThemePreference = ThemePreference.entries.find { it.name == str } ?: ThemePreference.SYSTEM

    actual fun getSavedThemePreferenceFlow(): Flow<ThemePreference> =
        if (ctx != null) {
            ctx.dataStore.data.map { preferences ->
                stringToTheme(preferences[THEME_KEY])
            }
        } else {
            // Fallback to default when context is not available
            flow { emit(ThemePreference.SYSTEM) }
        }

    actual suspend fun saveThemePreference(pref: ThemePreference) {
        if (ctx != null) {
            withContext(Dispatchers.IO) {
                ctx.dataStore.edit { prefs ->
                    prefs[THEME_KEY] = themeToString(pref)
                }
            }
        }
        // If context is null, we silently ignore the save operation
    }
}

@Composable
actual fun provideThemePreferenceRepository(): ThemePreferenceRepository {
    val context = LocalContext.current
    return ThemePreferenceRepository(context)
}
