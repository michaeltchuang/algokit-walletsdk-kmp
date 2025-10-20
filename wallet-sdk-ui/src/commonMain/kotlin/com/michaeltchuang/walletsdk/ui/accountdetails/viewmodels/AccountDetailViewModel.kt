package com.michaeltchuang.walletsdk.ui.accountdetails.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.account.domain.usecase.core.NameRegistrationUseCase
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import com.michaeltchuang.walletsdk.core.network.domain.usecase.GetCurrentNetworkUseCase
import com.michaeltchuang.walletsdk.core.network.model.AlgorandNetwork
import kotlinx.coroutines.launch

class AccountDetailViewModel(
    private val nameRegistrationUseCase: NameRegistrationUseCase,
    private val getCurrentNetworkUseCase: GetCurrentNetworkUseCase,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<AccountDetailViewModel.ViewState> by stateDelegate,
    EventViewModel<AccountDetailViewModel.ViewEvent> by eventDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        loadAccountState()
    }

    private fun loadAccountState() {
        viewModelScope.launch {
            getCurrentNetworkUseCase().collect { network ->
                stateDelegate.updateState {
                    ViewState.Content(
                        currentNetwork = network,
                        isTestNet = network == AlgorandNetwork.TESTNET,
                    )
                }
            }
        }
    }

    fun deleteAccount(address: String) {
        viewModelScope.launch {
            try {
                nameRegistrationUseCase.deleteAccount(address)
                eventDelegate.sendEvent(ViewEvent.AccountDeleted("Account deleted successfully."))
            } catch (e: Exception) {
                eventDelegate.sendEvent(
                    ViewEvent.Error(
                        e.message ?: "Failed to delete account.",
                    ),
                )
            }
        }
    }

    sealed interface ViewState {
        data object Idle : ViewState

        data object Loading : ViewState

        data class Content(
            val currentNetwork: AlgorandNetwork,
            val isTestNet: Boolean,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data class Error(
            val message: String,
        ) : ViewEvent

        data class AccountDeleted(
            val message: String,
        ) : ViewEvent
    }
}
