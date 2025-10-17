package com.michaeltchuang.walletsdk.ui.settings.domain.localization

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

expect class LocalizationPreferenceRepository(
    context: Any? = null,
) {
    fun getSavedLocalizationPreferenceFlow(): Flow<LocalizationPreference>

    suspend fun saveLocalizationPreference(pref: LocalizationPreference)
}

@Composable
expect fun provideLocalizationPreferenceRepository(): LocalizationPreferenceRepository

enum class LocalizationPreference {
    ENGLISH,
    ITALIAN,
}
