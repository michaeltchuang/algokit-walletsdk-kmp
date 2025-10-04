package com.michaeltchuang.walletsdk.settings.di

import com.michaeltchuang.walletsdk.account.presentation.viewmodels.AccountStatusViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.CreateAccountNameViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.HDWalletSelectionViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.OnboardingAccountTypeViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.QRScannerViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.RecoverPassphraseViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.ViewPassphraseViewModel
import com.michaeltchuang.walletsdk.settings.presentation.viewmodels.DeveloperSettingsViewModel
import com.michaeltchuang.walletsdk.settings.presentation.viewmodels.Falcon24WalletSelectionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val settingsModule =
    module {
        viewModel {
            DeveloperSettingsViewModel(
                get(),
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
                get(),
            )
        }
    }
