
package com.michaeltchuang.walletsdk.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackTransactionRequest(
    @SerialName("transaction_id")
    val transactionId: String,
)
