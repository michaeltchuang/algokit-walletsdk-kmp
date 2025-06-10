 

package com.michaeltchuang.walletsdk.algosdk.domain.usecase

import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39Sdk

class RecoverWithPassphrasePreviewUseCase(
    private val peraBip39Sdk: AlgoKitBip39Sdk,
) {
    fun getAccount(
        mnemonics: String,
    ): String {
        val entropy = peraBip39Sdk.getEntropyFromMnemonic(mnemonics)
        return entropy.contentToString()
    }
}
