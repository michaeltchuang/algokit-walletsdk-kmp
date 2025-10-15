package com.michaeltchuang.walletsdk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionParams(
    @SerialName("min-fee")
    val minFee: Long?,
    @SerialName("fee")
    var fee: Long,
    @SerialName("genesis-id")
    val genesisId: String,
    @SerialName("genesis-hash")
    val genesisHash: String,
    @SerialName("last-round")
    val lastRound: Long,
)
