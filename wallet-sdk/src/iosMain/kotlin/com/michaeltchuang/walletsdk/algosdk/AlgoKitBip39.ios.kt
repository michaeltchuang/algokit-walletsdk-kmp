package com.michaeltchuang.walletsdk.algosdk

import fr.acinq.bitcoin.MnemonicCode

internal actual fun getSeedFromEntropy(entropy: ByteArray) {
}
@OptIn(ExperimentalStdlibApi::class)
internal actual fun getEntropyFromMnemonic(mnemonic: String): String {
    val passphrase = ""
    val seed = MnemonicCode.toSeed(mnemonic, passphrase)
    return seed.toHexString()
}
internal actual fun getMnemonicFromEntropy(entropy: ByteArray) {}
