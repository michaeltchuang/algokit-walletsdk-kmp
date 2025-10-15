package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk

import com.algorand.algosdk.sdk.Sdk

internal class AlgoTransactionSignerImpl : AlgoTransactionSigner {
    override fun signWithSecretKey(
        secretKey: ByteArray,
        transaction: ByteArray,
    ): ByteArray? =
        try {
            Sdk.signTransaction(secretKey, transaction)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }

    override fun attachSignature(
        signature: ByteArray,
        transaction: ByteArray?,
    ): ByteArray? =
        try {
            Sdk.attachSignature(signature, transaction)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }

    override fun attachSignatureWithSigner(
        signature: ByteArray,
        transaction: ByteArray?,
        address: String?,
    ): ByteArray? =
        try {
            Sdk.attachSignatureWithSigner(signature, transaction, address)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
}
