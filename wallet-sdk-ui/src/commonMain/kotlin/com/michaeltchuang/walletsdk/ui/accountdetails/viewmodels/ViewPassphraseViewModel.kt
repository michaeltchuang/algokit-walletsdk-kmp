package com.michaeltchuang.walletsdk.ui.accountdetails.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.account.domain.model.local.AccountMnemonic
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAccountMnemonic
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import kotlinx.coroutines.launch

class ViewPassphraseViewModel(
    private val getAccountMnemonic: GetAccountMnemonic,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<ViewPassphraseViewModel.ViewState> by stateDelegate,
    EventViewModel<ViewPassphraseViewModel.ViewEvent> by eventDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Idle)
    }

    fun initViewState(accountAddress: String) {
        stateDelegate.onState<ViewState.Idle> {
            stateDelegate.updateState { ViewState.Loading }
            viewModelScope.launch {
                getAccountMnemonic(accountAddress).use(
                    onSuccess = ::displayMnemonic,
                    onFailed = { _, _ -> displayError() },
                )
            }
        }
    }

    private fun displayMnemonic(mnemonic: AccountMnemonic) {
        stateDelegate.updateState {
            ViewState.Content(mnemonic.words)
        }
    }

    private fun displayError() {
        viewModelScope.launch {
            eventDelegate.sendEvent(ViewEvent.ShowGenericError)
        }
    }

    sealed interface ViewState {
        data object Idle : ViewState

        data object Loading : ViewState

        data class Content(
            val mnemonicWords: List<String>,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data object ShowGenericError : ViewEvent
    }
}
