package com.michaeltchuang.walletsdk.algosdk

import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.AlgorandBip39WalletProvider
import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.Bip39Wallet
import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoAccountSdkImpl

actual fun recoverAlgo25Account(mnemonic: String): Algo25Account? {
    return AlgoAccountSdkImpl().recoverAlgo25Account(mnemonic = mnemonic)
}

actual fun createAlgo25Account(): Algo25Account? {
    return AlgoAccountSdkImpl().createAlgo25Account()
}

actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray) {}

actual fun createBip39Wallet(): Bip39Wallet {
    return AlgorandBip39WalletProvider().createBip39Wallet()
}

actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet {
    return AlgorandBip39WalletProvider().getBip39Wallet(entropy)
}
