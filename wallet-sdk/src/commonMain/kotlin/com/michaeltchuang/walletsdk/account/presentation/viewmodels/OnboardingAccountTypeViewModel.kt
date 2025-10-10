package com.michaeltchuang.walletsdk.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.account.data.mapper.entity.AccountCreationFalcon24TypeMapper
import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdSeedRepository
import com.michaeltchuang.walletsdk.algosdk.createBip39Wallet
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import com.michaeltchuang.walletsdk.foundation.StateDelegate
import com.michaeltchuang.walletsdk.foundation.StateViewModel
import com.michaeltchuang.walletsdk.foundation.utils.CreationType
import com.michaeltchuang.walletsdk.foundation.utils.manager.AccountCreationManager
import kotlinx.coroutines.launch

class OnboardingAccountTypeViewModel(
  /*  private val androidEncryptionManager: AndroidEncryptionManager,
    private val aesPlatformManager: AESPlatformManager,
    private val runtimeAwareSdk: RuntimeAwareSdk, */
    private val accountCreationFalcon24TypeMapper: AccountCreationFalcon24TypeMapper,
    private val hdSeedRepository: HdSeedRepository,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<OnboardingAccountTypeViewModel.ViewState> by stateDelegate,
    EventViewModel<OnboardingAccountTypeViewModel.ViewEvent> by eventDelegate {
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

    fun createFalcon24Account() {
        viewModelScope.launch {
            val wallet = createBip39Wallet()
            val mnemonic = wallet.getMnemonic().words.joinToString(" ")
            val falcon24 = wallet.generateFalcon24Address(mnemonic)
            val falcon24Type =
                accountCreationFalcon24TypeMapper(
                    wallet.getEntropy().value,
                    falcon24,
                    seedId = null,
                )
            val accountCreation =
                AccountCreation(
                    address = falcon24.address,
                    customName = null,
                    isBackedUp = false,
                    type = falcon24Type,
                    creationType = CreationType.CREATE,
                )

            AccountCreationManager.storePendingAccountCreation(accountCreation)
            eventDelegate.sendEvent(
                ViewEvent.AccountCreated(
                    accountCreation,
                ),
            )
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
