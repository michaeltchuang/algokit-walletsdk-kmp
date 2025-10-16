package com.michaeltchuang.walletsdk.ui.settings.theme

import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

private const val THEME_KEY = "theme_key"

actual class ThemePreferenceRepository actual constructor(
    context: Any?,
) {
    private val stateFlow = MutableStateFlow(ThemePreference.SYSTEM)
    private val defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults

    init {
        val saved = defaults.objectForKey(THEME_KEY) as? String
        stateFlow.value = saved?.let { name -> ThemePreference.entries.find { it.name == name } }
            ?: ThemePreference.SYSTEM
    }

    actual fun getSavedThemePreferenceFlow(): Flow<ThemePreference> = stateFlow.asStateFlow()

    actual suspend fun saveThemePreference(pref: ThemePreference) {
        withContext(Dispatchers.Default) {
            defaults.setValue(
                value = pref.name,
                forKey = THEME_KEY,
            )
            stateFlow.value = pref
        }
    }
}

@Composable
actual fun provideThemePreferenceRepository(): ThemePreferenceRepository = ThemePreferenceRepository()
