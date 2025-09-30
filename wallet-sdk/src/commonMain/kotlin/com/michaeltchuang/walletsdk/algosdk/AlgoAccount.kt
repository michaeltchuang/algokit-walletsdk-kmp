package com.michaeltchuang.walletsdk.algosdk

import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.Bip39Wallet
import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account


expect fun recoverAlgo25Account(mnemonic: String): Algo25Account?

expect fun createAlgo25Account(): Algo25Account?

expect fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray):String?

expect fun getBip39Wallet(entropy: ByteArray): Bip39Wallet

expect fun createBip39Wallet(): Bip39Wallet

expect fun getSeedFromEntropy(entropy: ByteArray): ByteArray?
