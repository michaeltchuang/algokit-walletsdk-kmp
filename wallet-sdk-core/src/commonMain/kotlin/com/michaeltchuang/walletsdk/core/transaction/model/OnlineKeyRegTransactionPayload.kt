package com.michaeltchuang.walletsdk.core.transaction.model

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.michaeltchuang.walletsdk.core.network.model.TransactionParams

data class OnlineKeyRegTransactionPayload(
    val senderAddress: String,
    val selectionPublicKey: String,
    val stateProofKey: String,
    val voteKey: String,
    val voteFirstRound: String,
    val voteLastRound: String,
    val voteKeyDilution: String,
    val txnParams: TransactionParams,
    val note: String?,
    val flatFee: BigInteger?,
)
