package com.michaeltchuang.walletsdk.account.di

import com.michaeltchuang.walletsdk.account.presentation.viewmodels.AccountStatusViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.CreateAccountNameViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.HDWalletSelectionViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.OnboardingAccountTypeViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.QRScannerViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.RecoverPassphraseViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.ViewPassphraseViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val viewModelModule =
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
        viewModel {
            ViewPassphraseViewModel(get(), get(), get())
        }
        viewModel {
            AccountStatusViewModel(get(), get())
        }
    }
