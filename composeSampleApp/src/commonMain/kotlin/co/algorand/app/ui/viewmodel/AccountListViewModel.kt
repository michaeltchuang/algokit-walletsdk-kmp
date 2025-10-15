package co.algorand.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.account.domain.model.custom.AccountLite
import com.michaeltchuang.walletsdk.account.domain.usecase.core.NameRegistrationUseCase
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import com.michaeltchuang.walletsdk.foundation.StateDelegate
import com.michaeltchuang.walletsdk.foundation.StateViewModel
import com.michaeltchuang.walletsdk.network.model.AlgorandNetwork
import com.michaeltchuang.walletsdk.network.model.ApiResult
import com.michaeltchuang.walletsdk.network.service.AccountInformationApiService
import com.michaeltchuang.walletsdk.network.service.getBasicAccountInformation
import com.michaeltchuang.walletsdk.ui.settings.screens.networkNodeSettings
import kotlinx.coroutines.launch

class AccountListViewModel(
    private val nameRegistrationUseCase: NameRegistrationUseCase,
    private val accountApiService: AccountInformationApiService,
    private val stateDelegate: StateDelegate<AccountsState>,
    private val eventDelegate: EventDelegate<AccountsEvent>,
) : ViewModel(),
    StateViewModel<AccountListViewModel.AccountsState> by stateDelegate,
    EventViewModel<AccountListViewModel.AccountsEvent> by eventDelegate {
    var accountLite = emptyList<AccountLite>()
    private var currentNetwork: AlgorandNetwork? = null

    init {
        stateDelegate.setDefaultState(AccountsState.Idle)

        // Listen for network changes and refetch accounts when network changes
        viewModelScope.launch {
            networkNodeSettings.collect { network ->
                fetchAccounts()
            }
        }
    }

    fun fetchAccounts() {
        stateDelegate.updateState { AccountsState.Loading }
        viewModelScope.launch {
            try {
                accountLite = nameRegistrationUseCase.getAccountLite()

                // Fetch account details for all accounts to get their amounts
                val accountsWithAmounts =
                    accountLite.map { account ->
                        val accountInfo = getAccountDetails(account.address)
                        account.copy(balance = accountInfo?.amount ?: "0")
                    }

                accountLite = accountsWithAmounts
                stateDelegate.updateState {
                    AccountsState.Content(accountLite)
                }
            } catch (e: Exception) {
                stateDelegate.updateState { AccountsState.Error(e.message ?: "Unknown error") }
                eventDelegate.sendEvent(
                    AccountsEvent.ShowError(
                        e.message ?: "Failed to fetch accounts.",
                    ),
                )
            }
        }
    }

    fun deleteAccount(address: String) {
        stateDelegate.updateState { AccountsState.Loading }
        viewModelScope.launch {
            try {
                nameRegistrationUseCase.deleteAccount(address)
                eventDelegate.sendEvent(AccountsEvent.ShowMessage("Account deleted successfully."))
                fetchAccounts() // Refresh the list after deletion
            } catch (e: Exception) {
                stateDelegate.updateState { AccountsState.Error(e.message ?: "Unknown error") }
                eventDelegate.sendEvent(
                    AccountsEvent.ShowError(
                        e.message ?: "Failed to delete account.",
                    ),
                )
            }
        }
    }

    private suspend fun getAccountDetails(address: String): com.michaeltchuang.walletsdk.network.model.AccountInformation? {
        val result = accountApiService.getBasicAccountInformation(address)
        return when (result) {
            is ApiResult.Success -> {
                println("Account information: ${result.data.accountInformation?.amount}")
                result.data.accountInformation
            }

            is ApiResult.Error -> {
                println("Account information error: ${result.message}")
                null
            }

            is ApiResult.NetworkError -> {
                println("Account information network error: ${result.exception.message}")
                null
            }
        }
    }

    sealed interface AccountsState {
        data object Idle : AccountsState

        data object Loading : AccountsState

        data class Content(
            val accounts: List<AccountLite>,
        ) : AccountsState

        data class Error(
            val message: String,
        ) : AccountsState
    }

    sealed interface AccountsEvent {
        data class ShowError(
            val message: String,
        ) : AccountsEvent

        data class ShowMessage(
            val message: String,
        ) : AccountsEvent
    }
}
