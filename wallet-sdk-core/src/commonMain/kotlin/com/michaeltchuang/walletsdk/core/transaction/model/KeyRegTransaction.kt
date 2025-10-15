package com.michaeltchuang.walletsdk.core.transaction.model

data class KeyRegTransaction(
    override val transactionByteArray: ByteArray?,
    override val accountAddress: String,
    override val accountAuthAddress: String?,
    override val isRekeyedToAnotherAccount: Boolean,
) : ExternalTransaction
