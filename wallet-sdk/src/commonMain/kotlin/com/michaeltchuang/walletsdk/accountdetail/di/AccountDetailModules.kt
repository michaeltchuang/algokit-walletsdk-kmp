package com.michaeltchuang.walletsdk.accountdetail.di

import com.michaeltchuang.walletsdk.accountdetail.presentation.viewmodels.AccountDetailViewModel
import com.michaeltchuang.walletsdk.accountdetail.presentation.viewmodels.ViewPassphraseViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val accountDetailModules =
    listOf(
        module {
            viewModel {
                ViewPassphraseViewModel(get(), get(), get())
            }
            viewModel {
                AccountDetailViewModel(get(), get())
            }
        },
    )
