package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.model

data class RemoveAssetTransactionPayload(
    val senderAddress: String,
    val creatorAddress: String,
    val assetId: Long,
)
