package com.michaeltchuang.walletsdk.foundation.designsystem.theme

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowInsetsControllerCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.dataStore by preferencesDataStore(name = "theme_preferences")
private val THEME_KEY = stringPreferencesKey("theme_key")

@Composable
internal actual fun SystemAppearance(isDark: Boolean) {
    val view = LocalView.current
    LaunchedEffect(isDark) {
        val window = (view.context as Activity).window
        WindowInsetsControllerCompat(window, window.decorView).apply {
            isAppearanceLightStatusBars = isDark
            isAppearanceLightNavigationBars = isDark
        }
    }
}

actual class ThemePreferenceRepository actual constructor(
    context: Any?,
) {
    private val ctx = context as? Context ?: error("Android Context required")

    fun themeToString(pref: ThemePreference): String = pref.name

    fun stringToTheme(str: String?): ThemePreference = ThemePreference.entries.find { it.name == str } ?: ThemePreference.SYSTEM

    actual fun getSavedThemePreferenceFlow(): Flow<ThemePreference> =
        ctx.dataStore.data.map { preferences ->
            stringToTheme(preferences[THEME_KEY])
        }

    actual suspend fun saveThemePreference(pref: ThemePreference) {
        withContext(Dispatchers.IO) {
            ctx.dataStore.edit { prefs ->
                prefs[THEME_KEY] = themeToString(pref)
            }
        }
    }
}

@Composable
actual fun provideThemePreferenceRepository(): ThemePreferenceRepository {
    val context = LocalContext.current
    return ThemePreferenceRepository(context)
}
