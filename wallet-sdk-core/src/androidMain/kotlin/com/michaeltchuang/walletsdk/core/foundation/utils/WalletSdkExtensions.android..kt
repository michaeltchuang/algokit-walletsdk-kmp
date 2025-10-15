@file:Suppress("TooManyFunctions")

package com.michaeltchuang.walletsdk.core.foundation.utils

import android.util.Base64
import com.algorand.algosdk.sdk.Sdk
import com.algorand.algosdk.sdk.SuggestedParams
import com.michaeltchuang.walletsdk.core.network.model.TransactionParams

const val ROUND_THRESHOLD = 1000L

actual typealias SuggestedParams = SuggestedParams
actual typealias TransactionParams = TransactionParams

actual fun TransactionParams.toSuggestedParams(addGenesisId: Boolean): SuggestedParams =
    SuggestedParams().apply {
        fee = this@toSuggestedParams.fee
        genesisID = if (addGenesisId) genesisId else ""
        firstRoundValid = lastRound
        lastRoundValid = lastRound + ROUND_THRESHOLD
        genesisHash = Base64.decode(this@toSuggestedParams.genesisHash, Base64.DEFAULT)
    }

fun String.urlSafeBase64ToStandard(): String =
    this
        .replace('-', '+')
        .replace('_', '/')
        .let {
            // Add padding if needed (base64 strings should be multiples of 4)
            val padding = (4 - it.length % 4) % 4
            it + "=".repeat(padding)
        }

actual fun ByteArray.signTransaction(secretKey: ByteArray): ByteArray = Sdk.signTransaction(secretKey, this)
