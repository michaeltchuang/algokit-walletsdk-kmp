package com.michaeltchuang.walletsdk.transaction.model

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.michaeltchuang.walletsdk.network.model.TransactionParams

data class OfflineKeyRegTransactionPayload(
    val senderAddress: String,
    val flatFee: BigInteger?,
    val note: String?,
    val txnParams: TransactionParams
)
