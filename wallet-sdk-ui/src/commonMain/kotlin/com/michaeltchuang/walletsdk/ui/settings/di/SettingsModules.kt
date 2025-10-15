package com.michaeltchuang.walletsdk.ui.settings.di

import com.michaeltchuang.walletsdk.ui.settings.viewmodels.DeveloperSettingsViewModel
import com.michaeltchuang.walletsdk.ui.settings.viewmodels.HDWalletSelectionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val settingsModules =
    listOf(
        module {
            viewModel {
                DeveloperSettingsViewModel(
                    get(),
                    get(),
                    get(),
                )
            }

            viewModel {
                HDWalletSelectionViewModel(
                    get(),
                    get(),
                    get(),
                    get(),
                    get(),
                )
            }
        },
    )
