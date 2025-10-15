package com.michaeltchuang.walletsdk.core.algosdk.domain.usecase

import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoKitBip39Sdk

class RecoverWithPassphrasePreviewUseCase(
    private val algokitBip39Sdk: AlgoKitBip39Sdk,
) {
    @OptIn(ExperimentalStdlibApi::class)
    fun getAccount(mnemonics: String): String {
        val entropy = algokitBip39Sdk.getEntropyFromMnemonic(mnemonics)
        return entropy?.toHexString()?.toString() ?: ""
//        val mnemonicCode = Mnemonics.MnemonicCode(entropy!!)
//        val seed = Bip39Seed(mnemonicCode.toSeed())
//        return seed.value.toHexString()
    }
}
