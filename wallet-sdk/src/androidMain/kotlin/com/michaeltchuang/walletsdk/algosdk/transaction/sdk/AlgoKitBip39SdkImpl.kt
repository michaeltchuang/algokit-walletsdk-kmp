

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk

import cash.z.ecc.android.bip39.Mnemonics
import cash.z.ecc.android.bip39.toSeed

internal class AlgoKitBip39SdkImpl : AlgoKitBip39Sdk {
    override fun getSeedFromEntropy(entropy: ByteArray): ByteArray? =
        try {
            Mnemonics.MnemonicCode(entropy).toSeed()
        } catch (e: Exception) {
            null
        }

    override fun getEntropyFromMnemonic(mnemonic: String): ByteArray? =
        try {
            Mnemonics.MnemonicCode(mnemonic).toEntropy()
        } catch (e: Exception) {
            null
        }

    override fun getMnemonicFromEntropy(entropy: ByteArray): String? =
        try {
            val mnemonic =
                Mnemonics.MnemonicCode(entropy).words.joinToString(" ") { charArray ->
                    String(charArray)
                }
            mnemonic
        } catch (e: Exception) {
            null
        }
}
