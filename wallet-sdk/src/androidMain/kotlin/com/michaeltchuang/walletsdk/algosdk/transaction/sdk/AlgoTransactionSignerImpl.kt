 

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk

import com.algorand.algosdk.sdk.Sdk

internal class AlgoTransactionSignerImpl () : AlgoTransactionSigner {

    override fun signWithSecretKey(secretKey: ByteArray, transaction: ByteArray): ByteArray? {
        return try {
            Sdk.signTransaction(secretKey, transaction)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    override fun attachSignature(signature: ByteArray, transaction: ByteArray?): ByteArray? {
        return try {
            Sdk.attachSignature(signature, transaction)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    override fun attachSignatureWithSigner(
        signature: ByteArray,
        transaction: ByteArray?,
        address: String?
    ): ByteArray? {
        return try {
            Sdk.attachSignatureWithSigner(signature, transaction, address)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }
}
