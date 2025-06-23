

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk

interface AlgoKitBip39Sdk {
    fun getSeedFromEntropy(entropy: ByteArray): ByteArray?

    fun getEntropyFromMnemonic(mnemonic: String): ByteArray?

    fun getMnemonicFromEntropy(entropy: ByteArray): String?
}
