package com.michaeltchuang.walletsdk.deeplink

import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.parser.CreateDeepLink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeeplinkHandler(
    private val createDeepLink: CreateDeepLink,
) {
    private var _deepLinkState = MutableSharedFlow<DeepLinkState>()
    val deepLinkState: SharedFlow<DeepLinkState> = _deepLinkState

    suspend fun handleDeepLink(uri: String) {
        withContext(Dispatchers.Main) {
            val parsedDeepLink = createDeepLink(uri)
            handleDeepLink(parsedDeepLink)
        }
    }

    private fun handleDeepLink(deepLink: DeepLink) {
        when (deepLink) {
            is DeepLink.Mnemonic -> handleMnemonicDeepLink(deepLink)
            is DeepLink.KeyReg -> handleKeyReg(deepLink)
            else -> {
                handleUnrecognizedDeepLink()
            }
        }
    }

    private fun handleMnemonicDeepLink(deepLink: DeepLink.Mnemonic) {
        CoroutineScope(Dispatchers.Main).launch {
            _deepLinkState.emit(DeepLinkState.OnImportAccountDeepLink(deepLink.mnemonic))
        }
    }

    private fun handleKeyReg(deepLink: DeepLink.KeyReg) {
        CoroutineScope(Dispatchers.Main).launch {
            _deepLinkState.emit(DeepLinkState.KeyReg(deepLink))
        }
    }

    private fun handleUnrecognizedDeepLink() {
        CoroutineScope(Dispatchers.Main).launch {
            _deepLinkState.emit(DeepLinkState.OnUnrecognizedDeepLink)
        }
    }

    sealed class DeepLinkState {
        data class OnImportAccountDeepLink(
            var mnemonic: String,
        ) : DeepLinkState()

        data class KeyReg(
            var keyReg: DeepLink.KeyReg,
        ) : DeepLinkState()

        object OnUnrecognizedDeepLink : DeepLinkState()
    }
}
