package com.michaeltchuang.walletsdk.settings.di

import com.michaeltchuang.walletsdk.account.presentation.viewmodels.Falcon24WalletSelectionViewModel
import com.michaeltchuang.walletsdk.settings.presentation.viewmodels.DeveloperSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val settingsModule =
    module {
        viewModel {
            DeveloperSettingsViewModel(
                get(),
                get(),
                get(),
            )
        }

        viewModel {
            Falcon24WalletSelectionViewModel(
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }
    }
