package com.michaeltchuang.walletsdk.ui.settings.domain.localization

import androidx.compose.runtime.Composable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

private const val LOCALIZATION_KEY = "localization_key"

actual class LocalizationPreferenceRepository actual constructor(
    context: Any?,
) {
    private val stateFlow = MutableStateFlow(LocalizationPreference.ENGLISH)
    private val defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults

    init {
        val saved = defaults.objectForKey(LOCALIZATION_KEY) as? String
        stateFlow.value =
            saved?.let { name -> LocalizationPreference.entries.find { it.name == name } }
                ?: LocalizationPreference.ENGLISH
    }

    actual fun getSavedLocalizationPreferenceFlow(): Flow<LocalizationPreference> =
        stateFlow.asStateFlow()

    actual suspend fun saveLocalizationPreference(pref: LocalizationPreference) {
        withContext(Dispatchers.Default) {
            defaults.setValue(
                value = pref.name,
                forKey = LOCALIZATION_KEY,
            )
            stateFlow.value = pref
        }
    }
}

@Composable
actual fun provideLocalizationPreferenceRepository(): LocalizationPreferenceRepository =
    LocalizationPreferenceRepository()
