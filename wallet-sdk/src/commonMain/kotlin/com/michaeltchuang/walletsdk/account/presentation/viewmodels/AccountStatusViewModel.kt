package com.michaeltchuang.walletsdk.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.account.domain.usecase.core.NameRegistrationUseCase
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import kotlinx.coroutines.launch

class AccountStatusViewModel(
    private val nameRegistrationUseCase: NameRegistrationUseCase,
    private val eventDelegate: EventDelegate<AccountsEvent>,
) : ViewModel(),
    EventViewModel<AccountStatusViewModel.AccountsEvent> by eventDelegate {
    fun deleteAccount(address: String) {
        viewModelScope.launch {
            try {
                nameRegistrationUseCase.deleteAccount(address)
                eventDelegate.sendEvent(AccountsEvent.AccountDeleted("Account deleted successfully."))
            } catch (e: Exception) {
                eventDelegate.sendEvent(
                    AccountsEvent.ShowError(
                        e.message ?: "Failed to delete account.",
                    ),
                )
            }
        }
    }

    sealed interface AccountsEvent {
        data class ShowError(
            val message: String,
        ) : AccountsEvent

        data class AccountDeleted(
            val message: String,
        ) : AccountsEvent
    }
}
