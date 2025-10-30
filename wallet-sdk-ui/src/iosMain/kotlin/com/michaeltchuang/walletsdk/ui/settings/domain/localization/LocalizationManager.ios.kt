package com.michaeltchuang.walletsdk.ui.settings.domain.localization

import androidx.compose.runtime.Composable
import platform.Foundation.NSUserDefaults

actual class LocalizationManager actual constructor(
    private val context: Any?,
) {
    actual fun actuateLocalization(localizationPreference: LocalizationPreference): Any? {
        setDefaultLocale(localizationPreference)
        return Unit
    }
}

@Composable
actual fun provideLocalizationManager(): LocalizationManager = LocalizationManager(null)

actual fun setDefaultLocale(localizationPreference: LocalizationPreference) {
    val languageCode =
        when (localizationPreference) {
            LocalizationPreference.ENGLISH -> "en"
            LocalizationPreference.ITALIAN -> "it"
            LocalizationPreference.HINDI -> "hi"
        }

    NSUserDefaults.standardUserDefaults.setObject(
        arrayListOf(languageCode),
        "AppleLanguages",
    )
    NSUserDefaults.standardUserDefaults.synchronize()
}
