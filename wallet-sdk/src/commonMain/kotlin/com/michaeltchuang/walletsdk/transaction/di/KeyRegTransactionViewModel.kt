package com.michaeltchuang.walletsdk.transaction.di

import com.michaeltchuang.walletsdk.transaction.presentation.viewmodels.KeyRegTransactionViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val keyRegTransactionViewModelModule = module {
    viewModel {
        KeyRegTransactionViewModel(get(), get(), get(), get())
    }
}