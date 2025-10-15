package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk

interface AlgoTransactionSigner {
    fun signWithSecretKey(
        secretKey: ByteArray,
        transaction: ByteArray,
    ): ByteArray?

    fun attachSignature(
        signature: ByteArray,
        transaction: ByteArray?,
    ): ByteArray?

    fun attachSignatureWithSigner(
        signature: ByteArray,
        transaction: ByteArray?,
        address: String?,
    ): ByteArray?
}
