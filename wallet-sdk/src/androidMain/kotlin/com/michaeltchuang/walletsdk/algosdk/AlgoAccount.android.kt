package com.michaeltchuang.walletsdk.algosdk

import com.algorand.algosdk.sdk.Sdk
import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.AlgorandBip39WalletProvider
import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.Bip39Wallet
import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoAccountSdkImpl
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39SdkImpl
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoSdkNumberExtensions.toUint64
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.SignHdKeyTransactionImpl
import com.michaeltchuang.walletsdk.foundation.utils.toSuggestedParams
import com.michaeltchuang.walletsdk.transaction.model.OfflineKeyRegTransactionPayload
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.Security

actual fun recoverAlgo25Account(mnemonic: String): Algo25Account? = AlgoAccountSdkImpl().recoverAlgo25Account(mnemonic = mnemonic)

actual fun createAlgo25Account(): Algo25Account? = AlgoAccountSdkImpl().createAlgo25Account()

actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray): String? =
    AlgoAccountSdkImpl().getMnemonicFromAlgo25SecretKey(secretKey = secretKey)

actual fun createBip39Wallet(): Bip39Wallet = AlgorandBip39WalletProvider().createBip39Wallet()

actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet = AlgorandBip39WalletProvider().getBip39Wallet(entropy)

actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray? {
    val seed = AlgoKitBip39SdkImpl().getSeedFromEntropy(entropy)
    return seed
}

actual fun signHdKeyTransaction(
    transactionByteArray: ByteArray,
    seed: ByteArray,
    account: Int,
    change: Int,
    key: Int,
): ByteArray? {
    Security.removeProvider("BC")
    Security.insertProviderAt(BouncyCastleProvider(), 0)
    return SignHdKeyTransactionImpl().signTransaction(
        transactionByteArray,
        seed,
        account,
        change,
        key,
    )
}

actual fun sdkSignTransaction(
    secretKey: ByteArray,
    signTx: ByteArray,
): ByteArray {
    Security.removeProvider("BC")
    Security.insertProviderAt(BouncyCastleProvider(), 0)
    val signedTx = Sdk.signTransaction(secretKey, signTx)
    return signedTx
}

actual fun createTransaction(payload: OfflineKeyRegTransactionPayload): ByteArray =
    with(payload) {
        val suggestedParams = txnParams.toSuggestedParams()
        if (flatFee != null) {
            suggestedParams.fee = flatFee.toString().toLong()
            suggestedParams.flatFee = true
        }

        val defaultVoteValue =
            java.math.BigInteger.ZERO
                .toUint64()

        Sdk.makeKeyRegTxnWithStateProofKey(
            senderAddress,
            note?.toByteArray(),
            suggestedParams,
            null,
            null,
            null,
            defaultVoteValue,
            defaultVoteValue,
            defaultVoteValue,
            false,
        )
    }
