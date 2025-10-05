package com.michaeltchuang.walletsdk.settings.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.account.data.mapper.entity.AccountCreationFalcon24TypeMapper
import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdSeedRepository
import com.michaeltchuang.walletsdk.algosdk.createAlgo25Account
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import com.michaeltchuang.walletsdk.foundation.StateDelegate
import com.michaeltchuang.walletsdk.foundation.StateViewModel
import com.michaeltchuang.walletsdk.utils.CreationType
import com.michaeltchuang.walletsdk.utils.manager.AccountCreationManager
import kotlinx.coroutines.launch

class DeveloperSettingsViewModel(
    /*  private val androidEncryptionManager: AndroidEncryptionManager,
      private val aesPlatformManager: AESPlatformManager,
      private val runtimeAwareSdk: RuntimeAwareSdk, */
    private val hdSeedRepository: HdSeedRepository,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<DeveloperSettingsViewModel.ViewState> by stateDelegate,
    EventViewModel<DeveloperSettingsViewModel.ViewEvent> by eventDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        //  viewModelScope.launch { androidEncryptionManager.initializeEncryptionManager() }
        hasAnySeedExist()
    }

    private fun hasAnySeedExist() {
        viewModelScope.launch {
            hdSeedRepository.hasAnySeed().let { hasAnySeed ->
                stateDelegate.updateState {
                    ViewState.Content(hasAnySeed)
                }
            }
        }
    }

    fun createAlgoAccount() {
        viewModelScope.launch {
            try {
                createAlgo25Account()?.let {
                    val secretKey = it.secretKey
                    val accountCreation =
                        AccountCreation(
                            address = it.address,
                            customName = null,
                            isBackedUp = false,
                            type = AccountCreation.Type.Algo25(secretKey),
                            creationType = CreationType.CREATE,
                        )
                    // Store the account creation data in the manager
                    AccountCreationManager.storePendingAccountCreation(accountCreation = accountCreation)
                    eventDelegate.sendEvent(ViewEvent.AccountCreated(accountCreation = accountCreation))
                } ?: run {
                    displayError("Failed to create account")
                }
            } catch (e: Exception) {
                displayError(e.message ?: "Unknown error")
            }
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
            val hasAnySeed: Boolean,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data class AccountCreated(
            val accountCreation: AccountCreation,
        ) : ViewEvent

        data class Error(
            val message: String,
        ) : ViewEvent
    }
}
