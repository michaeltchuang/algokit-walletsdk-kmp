package com.michaeltchuang.walletsdk.ui.settings.di

import com.michaeltchuang.walletsdk.ui.settings.domain.localization.LocalizationPreferenceRepository
import com.michaeltchuang.walletsdk.ui.settings.domain.theme.ThemePreferenceRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module =
    module {
        single<ThemePreferenceRepository> {
            ThemePreferenceRepository()
        }
        single<LocalizationPreferenceRepository> {
            LocalizationPreferenceRepository()
        }
    }
