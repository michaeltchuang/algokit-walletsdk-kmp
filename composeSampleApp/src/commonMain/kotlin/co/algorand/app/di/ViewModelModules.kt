package co.algorand.app.di

import co.algorand.app.ui.viewmodel.AccountListViewModel
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val provideViewModelModules =
    module {
        single { SnackbarViewModel() }
        viewModel<AccountListViewModel> {
            AccountListViewModel(
                get(),
                get(),
                get(),
            )
        }
    }
