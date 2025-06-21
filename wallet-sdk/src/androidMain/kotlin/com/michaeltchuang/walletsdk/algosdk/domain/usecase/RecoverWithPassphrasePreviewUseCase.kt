 

package com.michaeltchuang.walletsdk.algosdk.domain.usecase

import cash.z.ecc.android.bip39.Mnemonics
import cash.z.ecc.android.bip39.toSeed
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Bip39Seed
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39Sdk

class RecoverWithPassphrasePreviewUseCase(
    private val algokitBip39Sdk: AlgoKitBip39Sdk,
) {
    @OptIn(ExperimentalStdlibApi::class)
    fun getAccount(
        mnemonics: String,
    ): String {
        val entropy = algokitBip39Sdk.getEntropyFromMnemonic(mnemonics)
        val mnemonicCode = Mnemonics.MnemonicCode(entropy!!)
        val seed = Bip39Seed(mnemonicCode.toSeed())
        return seed.value.toHexString()
    }
}
