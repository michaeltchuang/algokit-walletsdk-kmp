package com.michaeltchuang.walletsdk.core.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendTransactionResponse(
    @SerialName("txId")
    val txnId: String? = null,
)
