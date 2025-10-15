package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.model

import java.math.BigInteger

data class AlgoTransactionPayload(
    val senderAddress: String,
    val receiverAddress: String,
    val amount: BigInteger,
    val noteInByteArray: ByteArray?,
    val isMaxAmount: Boolean,
)
