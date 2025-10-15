package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk

interface SignFalcon24Transaction {
    fun signTransaction(
        transactionByteArray: ByteArray,
        publicKey: ByteArray,
        privateKey: ByteArray,
    ): ByteArray?
}
