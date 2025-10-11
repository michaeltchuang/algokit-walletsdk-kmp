package com.michaeltchuang.walletsdk.transaction.model

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.michaeltchuang.walletsdk.network.model.TransactionParams

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
