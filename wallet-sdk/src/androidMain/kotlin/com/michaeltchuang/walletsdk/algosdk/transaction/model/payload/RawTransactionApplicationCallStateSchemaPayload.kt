 

package com.michaeltchuang.walletsdk.algosdk.transaction.model.payload

import com.google.gson.annotations.SerializedName
import java.math.BigInteger

internal data class RawTransactionApplicationCallStateSchemaPayload(
    @SerializedName("nui") val numberOfInts: BigInteger? = null,
    @SerializedName("nbs") val numberOfBytes: BigInteger? = null
)
