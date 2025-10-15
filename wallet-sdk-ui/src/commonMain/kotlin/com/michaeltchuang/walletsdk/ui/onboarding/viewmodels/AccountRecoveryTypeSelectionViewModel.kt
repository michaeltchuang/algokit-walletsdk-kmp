package com.michaeltchuang.walletsdk.ui.onboarding.viewmodels

import androidx.lifecycle.ViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel

class AccountRecoveryTypeSelectionViewModel(
    // private val sharedPref: SharedPreferences,
    // private val isThereAnyLocalAccount: IsThereAnyLocalAccount,
    private val stateDelegate: StateDelegate<ViewState>,
    // private val isOnHdWalletUseCase: IsOnHdWalletUseCase
) : ViewModel(),
    StateViewModel<AccountRecoveryTypeSelectionViewModel.ViewState> by stateDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Idle)
    }

    fun setRegisterSkip() {
        // sharedPref.setRegisterSkip()
    }

    fun setupToolbar() {
        /*  viewModelScope.launchIO {
              if (!isThereAnyLocalAccount()) {
                  stateDelegate.setDefaultState(ViewState.NoLocalAccountState)
              }
          }*/
    }

    fun isOnHdWallet(): Boolean {
        //   return isOnHdWalletUseCase.invoke()
        return true
    }

    /* fun logRecoverAccountTypeClickEvent(onboardingAccountType: OnboardingAccountType) {
         val clickEvent = when (onboardingAccountType) {
             OnboardingAccountType.HdKey -> PeraClickEvent.TAP_ONBOARDING_RECOVER_UNIVERSAL
             OnboardingAccountType.Algo25 -> PeraClickEvent.TAP_ONBOARDING_RECOVER_ALGO25
         }
         logEvent(clickEvent)
     }*/

    sealed interface ViewState {
        data object Idle : ViewState

        data object NoLocalAccountState : ViewState
    }
}
