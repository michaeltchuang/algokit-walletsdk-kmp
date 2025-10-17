package com.michaeltchuang.walletsdk.ui.settings.di

import com.michaeltchuang.walletsdk.ui.settings.domain.theme.ThemePreferenceRepository
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformThemeModule(): Module =
    module {
        single<ThemePreferenceRepository> { ThemePreferenceRepository() }
    }
