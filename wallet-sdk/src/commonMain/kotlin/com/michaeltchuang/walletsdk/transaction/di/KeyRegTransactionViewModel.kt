package com.michaeltchuang.walletsdk.transaction.di

import com.michaeltchuang.walletsdk.transaction.presentation.viewmodels.ConfirmTransactionRequestViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val confirmTransactionRequestViewModelModule =
    module {
        viewModel {
            ConfirmTransactionRequestViewModel(get(), get(), get(), get(), get())
        }
    }
