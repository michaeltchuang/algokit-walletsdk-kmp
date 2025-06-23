

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model

data class RemoveAssetTransactionPayload(
    val senderAddress: String,
    val creatorAddress: String,
    val assetId: Long,
)
