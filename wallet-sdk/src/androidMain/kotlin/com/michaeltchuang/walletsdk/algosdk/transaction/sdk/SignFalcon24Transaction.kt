package com.michaeltchuang.walletsdk.algosdk.transaction.sdk

interface SignFalcon24Transaction {
    fun signTransaction(
        transactionByteArray: ByteArray,
        publicKey: ByteArray,
        privateKey: ByteArray,
    ): ByteArray?
}
