package com.michaeltchuang.walletsdk.account.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.deeplink.DeeplinkHandler
import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class QRScannerViewModel(
    private val deeplinkHandler: DeeplinkHandler,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(), EventViewModel<QRScannerViewModel.ViewEvent> by eventDelegate {

    init {
        viewModelScope.launch {
            deeplinkHandler.deepLinkState.collect {
                when (it) {
                    is DeeplinkHandler.DeepLinkState.OnImportAccountDeepLink -> {
                        eventDelegate.sendEvent(ViewEvent.NavigateToRecoveryPhraseScreen(it.mnemonic))
                    }

                    is DeeplinkHandler.DeepLinkState.KeyReg -> {
                        eventDelegate.sendEvent(
                            ViewEvent.NavigateToTransactionSignatureRequestScreen(
                                it.keyReg
                            )
                        )
                    }

                    is DeeplinkHandler.DeepLinkState.OnUnrecognizedDeepLink -> {
                        eventDelegate.sendEvent(ViewEvent.ShowUnrecognizedDeeplink)
                    }
                }
            }
        }
    }

    fun handleDeeplink(uri: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deeplinkHandler.handleDeepLink(uri)
        }
    }

    interface ViewEvent {
        data class NavigateToRecoveryPhraseScreen(val mnemonic: String) : ViewEvent
        data class NavigateToTransactionSignatureRequestScreen(val keyReg: DeepLink.KeyReg) :
            ViewEvent

        object ShowUnrecognizedDeeplink : ViewEvent
    }
}
