package com.michaeltchuang.walletsdk.transaction.di

import com.michaeltchuang.walletsdk.transaction.presentation.viewmodels.PendingTransactionRequestViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val pendingTransactionRequestViewModelModule =
    module {
        viewModel {
            PendingTransactionRequestViewModel(get(), get(), get(), get(), get())
        }
    }
