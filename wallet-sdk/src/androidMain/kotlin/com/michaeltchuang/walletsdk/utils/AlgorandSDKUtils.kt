@file:Suppress("TooManyFunctions")

package com.michaeltchuang.walletsdk.utils

import android.util.Base64
import com.algorand.algosdk.sdk.SuggestedParams
import com.algorand.algosdk.sdk.Uint64
import com.michaeltchuang.walletsdk.network.model.TransactionParams
import java.math.BigInteger

const val ROUND_THRESHOLD = 1000L

fun TransactionParams.toSuggestedParams(
    addGenesisId: Boolean = true
): SuggestedParams {
    return SuggestedParams().apply {
        fee = this@toSuggestedParams.fee
        genesisID = if (addGenesisId) genesisId else ""
        firstRoundValid = lastRound
        lastRoundValid = lastRound + ROUND_THRESHOLD
        genesisHash = Base64.decode(this@toSuggestedParams.genesisHash, Base64.DEFAULT)
    }
}


fun Long.toUint64(): Uint64 {
    return Uint64().apply {
        upper = shr(Int.SIZE_BITS)
        lower = and(Int.MAX_VALUE.toLong())
    }
}

fun BigInteger.toUint64(): Uint64 {
    return Uint64().apply {
        upper = shr(Int.SIZE_BITS).toLong()
        lower = and(UInt.MAX_VALUE.toLong().toBigInteger()).toLong()
    }
}

