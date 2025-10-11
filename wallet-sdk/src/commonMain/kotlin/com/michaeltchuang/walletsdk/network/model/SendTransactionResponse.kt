package com.michaeltchuang.walletsdk.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendTransactionResponse(
    @SerialName("txId")
    val txnId: String? = null,
)
