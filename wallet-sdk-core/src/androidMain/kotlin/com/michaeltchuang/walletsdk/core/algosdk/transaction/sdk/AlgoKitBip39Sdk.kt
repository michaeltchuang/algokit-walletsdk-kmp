package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk

interface AlgoKitBip39Sdk {
    fun getSeedFromEntropy(entropy: ByteArray): ByteArray?

    fun getEntropyFromMnemonic(mnemonic: String): ByteArray?

    fun getMnemonicFromEntropy(entropy: ByteArray): String?
}
