package com.michaeltchuang.walletsdk.ui.settings.theme

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

expect class ThemePreferenceRepository(
    context: Any? = null,
) {
    fun getSavedThemePreferenceFlow(): Flow<ThemePreference>

    suspend fun saveThemePreference(pref: ThemePreference)
}

@Composable
expect fun provideThemePreferenceRepository(): ThemePreferenceRepository

enum class ThemePreference {
    LIGHT,
    DARK,
    SYSTEM,
    ;

    fun convertToSystemAbbr(): Int {
        // Android's AppCompatDelegate modes
        return when (this) {
            LIGHT -> 1 // MODE_NIGHT_NO
            DARK -> 2 // MODE_NIGHT_YES
            SYSTEM -> 0 // MODE_NIGHT_FOLLOW_SYSTEM
        }
    }
}
