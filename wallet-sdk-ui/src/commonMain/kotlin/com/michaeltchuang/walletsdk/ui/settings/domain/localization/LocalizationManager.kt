package com.michaeltchuang.walletsdk.ui.settings.domain.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf

expect class LocalizationManager(
    context: Any? = null
) {
    fun actuateLocalization(localizationPreference: LocalizationPreference): Any?
}

@Composable
expect fun provideLocalizationManager(): LocalizationManager

expect fun setDefaultLocale(localizationPreference: LocalizationPreference)

val LocalAppLocale = staticCompositionLocalOf { LocalizationPreference.ENGLISH }

@Composable
fun LocaleAwareContent(content: @Composable () -> Unit) {
    val currentLocale = LocalAppLocale.current

    LaunchedEffect(currentLocale) {
        setDefaultLocale(currentLocale)
    }

    content()
}