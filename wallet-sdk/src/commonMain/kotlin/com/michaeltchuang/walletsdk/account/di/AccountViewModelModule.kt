package com.michaeltchuang.walletsdk.account.di

import com.michaeltchuang.walletsdk.account.presentation.viewmodels.CreateAccountNameViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.OnboardingAccountTypeViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.QRScannerViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.RecoverPassphraseViewModel
import com.michaeltchuang.walletsdk.settings.presentation.viewmodels.HDWalletSelectionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val accountViewModelModule =
    module {
        viewModel {
            OnboardingAccountTypeViewModel(
                get(),
                get(),
                get(),
                get(),
            )
        }

        viewModel {
            CreateAccountNameViewModel(
                get(),
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

        viewModel {
            QRScannerViewModel(
                get(),
                get(),
            )
        }

        viewModel {
            RecoverPassphraseViewModel(get(), get())
        }
    }
