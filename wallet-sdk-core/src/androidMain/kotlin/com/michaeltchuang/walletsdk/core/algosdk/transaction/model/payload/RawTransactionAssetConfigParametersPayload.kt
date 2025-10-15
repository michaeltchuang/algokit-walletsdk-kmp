package com.michaeltchuang.walletsdk.core.algosdk.transaction.model.payload

import com.google.gson.annotations.SerializedName

internal data class RawTransactionAssetConfigParametersPayload(
    @SerializedName("t") val totalSupply: String?,
    @SerializedName("dc") val decimal: Long?,
    @SerializedName("df") val isFrozen: Boolean?,
    @SerializedName("un") val unitName: String?,
    @SerializedName("an") val name: String?,
    @SerializedName("au") val url: String?,
    @SerializedName("am") val metadataHash: String?,
    @SerializedName("m") val managerAddress: String?,
    @SerializedName("r") val reserveAddress: String?,
    @SerializedName("f") val frozenAddress: String?,
    @SerializedName("c") val clawbackAddress: String?,
)
