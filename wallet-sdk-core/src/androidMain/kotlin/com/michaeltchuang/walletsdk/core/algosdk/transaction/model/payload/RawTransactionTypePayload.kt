package com.michaeltchuang.walletsdk.core.algosdk.transaction.model.payload

import com.google.gson.annotations.SerializedName

internal enum class RawTransactionTypePayload {
    @SerializedName("pay")
    PAY_TRANSACTION,

    @SerializedName("axfer")
    ASSET_TRANSACTION,

    @SerializedName("appl")
    APP_TRANSACTION,

    @SerializedName("acfg")
    ASSET_CONFIGURATION,

    @SerializedName("keyreg")
    KEYREG_TRANSACTION,

    @SerializedName("hb")
    HEARTBEAT_TRANSACTION,

    UNDEFINED,
}
