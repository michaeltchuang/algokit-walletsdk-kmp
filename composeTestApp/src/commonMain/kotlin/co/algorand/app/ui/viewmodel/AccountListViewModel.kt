package co.algorand.app.ui.viewmodel

/*import com.michaeltchuang.walletsdk..Sdk
import com.michaeltchuang.walletsdk..account.domain.model.custom.AccountLite
import com.michaeltchuang.walletsdk..foundation.EventDelegate
import com.michaeltchuang.walletsdk..foundation.EventViewModel
import com.michaeltchuang.walletsdk..foundation.StateDelegate
import com.michaeltchuang.walletsdk..foundation.StateViewModel*/
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.account.domain.model.core.AccountRegistrationType
import com.michaeltchuang.walletsdk.account.domain.model.custom.AccountLite
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import com.michaeltchuang.walletsdk.foundation.StateDelegate
import com.michaeltchuang.walletsdk.foundation.StateViewModel
import kotlinx.coroutines.launch

class AccountListViewModel(
    /*  private val Sdk: Sdk,*/
    private val stateDelegate: StateDelegate<AccountsState>,
    private val eventDelegate: EventDelegate<AccountsEvent>,
) : ViewModel(),
    StateViewModel<AccountListViewModel.AccountsState> by stateDelegate,
    EventViewModel<AccountListViewModel.AccountsEvent> by eventDelegate {
    var accountLite = emptyList<AccountLite>()

    init {
        stateDelegate.setDefaultState(AccountsState.Idle)
    }

        fun fetchAccounts() {
            stateDelegate.updateState { AccountsState.Loading }
            viewModelScope.launch {
                try {
                 //   accountLite = Sdk.fetchAccountLite()
                    stateDelegate.updateState {
                        AccountsState.Content(dummyAccountData())
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

    /*    fun deleteAccount(address: String) {
            stateDelegate.updateState { AccountsState.Loading }
            viewModelScope.launch {
                try {
                    Sdk.deleteAccount(address)
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
        }*/

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

    private fun dummyAccountData(): List<AccountLite> {
        return listOf(
            AccountLite("address1", "name1", AccountRegistrationType.HdKey),
            AccountLite("address2", "name2", AccountRegistrationType.HdKey)
        )
    }
}
