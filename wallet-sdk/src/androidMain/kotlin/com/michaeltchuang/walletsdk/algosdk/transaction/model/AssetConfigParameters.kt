 

package com.michaeltchuang.walletsdk.algosdk.transaction.model

data class AssetConfigParameters(
    val totalSupply: String?,
    val decimal: Long?,
    val isFrozen: Boolean?,
    val unitName: String?,
    val name: String?,
    val url: String?,
    val metadataHash: String?,
    val managerAddress: String?,
    val reserveAddress: String?,
    val frozenAddress: String?,
    val clawbackAddress: String?
)
