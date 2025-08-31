package co.algorand.app.di

import co.algorand.app.ui.viewmodel.AccountListViewModel
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.CreateAccountNameViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.OnboardingAccountTypeViewModel
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.RecoverPassphraseViewModel
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.StateDelegate
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val provideViewModelModules =
    module {
        single { SnackbarViewModel() }
        factory { StateDelegate<Any>() }
        factory { EventDelegate<Any>() }
        viewModel<AccountListViewModel> {
            AccountListViewModel(
                get(),
                get(),
            )
        }
        viewModel<OnboardingAccountTypeViewModel> {
            OnboardingAccountTypeViewModel(
                get(),
                get(),
            )
        }

        viewModel<RecoverPassphraseViewModel> {
            RecoverPassphraseViewModel(
                get(),
            )
        }

        viewModel<CreateAccountNameViewModel> {
            CreateAccountNameViewModel(
                get(),
                get(),
            )
        }
    }
