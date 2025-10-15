package com.michaeltchuang.walletsdk.ui.signing.di

import com.michaeltchuang.walletsdk.ui.signing.viewmodels.ConfirmTransactionRequestViewModel
import com.michaeltchuang.walletsdk.ui.signing.viewmodels.TransactionSuccessViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

internal val signingModules =
    listOf(
        module {
            viewModel {
                ConfirmTransactionRequestViewModel(
                    get(),
                    get(),
                    get(),
                    get(),
                    get(),
                    get(),
                )
            }

            viewModel {
                TransactionSuccessViewModel(
                    get(),
                )
            }
        },
    )
