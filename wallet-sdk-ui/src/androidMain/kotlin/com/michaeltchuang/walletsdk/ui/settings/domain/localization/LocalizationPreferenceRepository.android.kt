package com.michaeltchuang.walletsdk.ui.settings.domain.localization

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

val Context.dataStore by preferencesDataStore(name = "application_preferences")
private val LOCALIZATION_KEY = stringPreferencesKey("localization_key")

actual class LocalizationPreferenceRepository actual constructor(
    context: Any?,
) {
    private val ctx = AndroidContextHolder.applicationContext

    fun themeToString(pref: LocalizationPreference): String = pref.name

    fun stringToTheme(str: String?): LocalizationPreference =
        LocalizationPreference.entries.find { it.name == str } ?: LocalizationPreference.ENGLISH

    actual fun getSavedLocalizationPreferenceFlow(): Flow<LocalizationPreference> =
        if (ctx != null) {
            ctx.dataStore.data.map { preferences ->
                stringToTheme(preferences[LOCALIZATION_KEY])
            }
        } else {
            // Fallback to default when context is not available
            flow { emit(LocalizationPreference.ENGLISH) }
        }

    actual suspend fun saveLocalizationPreference(pref: LocalizationPreference) {
        if (ctx != null) {
            withContext(Dispatchers.IO) {
                ctx.dataStore.edit { prefs ->
                    prefs[LOCALIZATION_KEY] = themeToString(pref)
                }
            }
        }
        // If context is null, we silently ignore the save operation
    }
}

@Composable
actual fun provideLocalizationPreferenceRepository(): LocalizationPreferenceRepository {
    val context = LocalContext.current
    return LocalizationPreferenceRepository(context)
}
