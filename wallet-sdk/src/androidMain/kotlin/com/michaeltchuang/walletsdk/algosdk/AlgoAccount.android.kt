package com.michaeltchuang.walletsdk.algosdk

import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.AlgorandBip39WalletProvider
import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.Bip39Wallet
import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoAccountSdkImpl
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39SdkImpl

actual fun recoverAlgo25Account(mnemonic: String): Algo25Account? {
    return AlgoAccountSdkImpl().recoverAlgo25Account(mnemonic = mnemonic)
}

actual fun createAlgo25Account(): Algo25Account? {
    return AlgoAccountSdkImpl().createAlgo25Account()
}

actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray): String? {
    return AlgoAccountSdkImpl().getMnemonicFromAlgo25SecretKey(secretKey = secretKey)
}

actual fun createBip39Wallet(): Bip39Wallet {
    return AlgorandBip39WalletProvider().createBip39Wallet()
}

actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet {
    return AlgorandBip39WalletProvider().getBip39Wallet(entropy)
}

actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray? {
    val seed = AlgoKitBip39SdkImpl().getSeedFromEntropy(entropy)
    return seed
}
