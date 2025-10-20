package com.michaeltchuang.walletsdk.ui.settings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import com.michaeltchuang.walletsdk.core.network.domain.usecase.GetCurrentNetworkUseCase
import com.michaeltchuang.walletsdk.core.network.domain.usecase.SaveNetworkPreferenceUseCase
import com.michaeltchuang.walletsdk.core.network.model.AlgorandNetwork
import kotlinx.coroutines.launch

class NodeSettingsViewModel(
    private val getCurrentNetworkUseCase: GetCurrentNetworkUseCase,
    private val saveNetworkPreferenceUseCase: SaveNetworkPreferenceUseCase,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<NodeSettingsViewModel.ViewState> by stateDelegate,
    EventViewModel<NodeSettingsViewModel.ViewEvent> by eventDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        loadCurrentNodePreference()
    }

    private fun loadCurrentNodePreference() {
        viewModelScope.launch {
            getCurrentNetworkUseCase().collect { network ->
                stateDelegate.updateState {
                    ViewState.Content(
                        currentNetwork = network,
                        networkOptions = AlgorandNetwork.values().toList(),
                    )
                }
            }
        }
    }

    fun onNetworkSelected(network: AlgorandNetwork) {
        viewModelScope.launch {
            try {
                // Get current network to check if it's different
                val currentState = stateDelegate.state.value
                if (currentState is ViewState.Content && network != currentState.currentNetwork) {
                    // If switching to MainNet, show warning first
                    if (network == AlgorandNetwork.MAINNET) {
                        eventDelegate.sendEvent(ViewEvent.ShowMainnetWarning(network))
                    } else {
                        saveNetworkPreference(network)
                    }
                }
            } catch (e: Exception) {
                displayError(e.message ?: "Failed to select network")
            }
        }
    }

    fun confirmMainnetSelection(network: AlgorandNetwork) {
        viewModelScope.launch {
            saveNetworkPreference(network)
        }
    }

    private suspend fun saveNetworkPreference(network: AlgorandNetwork) {
        try {
            saveNetworkPreferenceUseCase(network)
            eventDelegate.sendEvent(ViewEvent.NetworkChanged(network))
        } catch (e: Exception) {
            displayError(e.message ?: "Failed to save network preference")
        }
    }

    private fun displayError(message: String) {
        viewModelScope.launch {
            eventDelegate.sendEvent(ViewEvent.Error(message))
        }
    }

    sealed interface ViewState {
        data object Idle : ViewState

        data object Loading : ViewState

        data class Content(
            val currentNetwork: AlgorandNetwork,
            val networkOptions: List<AlgorandNetwork>,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data class NetworkChanged(
            val network: AlgorandNetwork,
        ) : ViewEvent

        data class ShowMainnetWarning(
            val network: AlgorandNetwork,
        ) : ViewEvent

        data class Error(
            val message: String,
        ) : ViewEvent
    }
}
