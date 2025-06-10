package com.michaeltchuang.walletsdk.algosdk

import androidx.compose.runtime.Composable

internal expect fun getSeedFromEntropy(entropy: ByteArray)
internal expect fun getEntropyFromMnemonic(mnemonic: String): String
internal expect fun getMnemonicFromEntropy(entropy: ByteArray)