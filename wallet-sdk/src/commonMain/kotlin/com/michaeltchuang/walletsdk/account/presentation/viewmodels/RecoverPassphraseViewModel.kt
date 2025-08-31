package com.michaeltchuang.walletsdk.account.presentation.viewmodels


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.account.domain.model.core.AccountCreation
import com.michaeltchuang.walletsdk.account.domain.model.core.OnboardingAccountType
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import com.michaeltchuang.walletsdk.utils.splitMnemonic
import kotlinx.coroutines.launch

class RecoverPassphraseViewModel(
    /*private val context: Application,
    private val recoverPassphrasePreviewUseCase: RecoverPassphraseUseCase,*/
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(), EventViewModel<RecoverPassphraseViewModel.ViewEvent> by eventDelegate {


    fun onRecoverAccount(
        mnemonic: String, onboardingAccountType: OnboardingAccountType
    ) {
        /*    viewModelScope.launch(Dispatchers.IO) {
                recoverPassphrasePreviewUseCase.validateEnteredMnemonics(
                    mnemonic, onboardingAccountType
                ).collectLatest { accountCreation ->
                    accountCreation?.let {
                        // Store the account creation data in the manager
                        AccountCreationManager.storePendingAccountCreation(it)
                        eventDelegate.sendEvent(ViewEvent.NavigateToAccountNameScreen(it))
                    }
                }
            }*/
    }

    fun onClipBoardPastedMnemonic(mnemonic: String, isValid: () -> Unit) {
        val splittedText = mnemonic.splitMnemonic()
        if (
            splittedText.size != OnboardingAccountType.Algo25.wordCount &&
            splittedText.size != OnboardingAccountType.HdKey.wordCount
        ) {
            viewModelScope.launch {
                eventDelegate.sendEvent(ViewEvent.ShowError("The last copied text is not a valid passphrase. Please copy a valid passphrase and try again."))
            }
        } else {
            isValid()
        }

    }

    interface ViewEvent {
        data class NavigateToAccountNameScreen(val accountCreation: AccountCreation) : ViewEvent
        data class ShowError(val error: String) : ViewEvent
    }
}
