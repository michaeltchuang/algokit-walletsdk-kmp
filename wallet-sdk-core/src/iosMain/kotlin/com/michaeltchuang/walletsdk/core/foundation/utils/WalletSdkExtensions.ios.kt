package com.michaeltchuang.walletsdk.core.foundation.utils

import com.michaeltchuang.walletsdk.core.network.model.TransactionParams

actual fun ByteArray.signTransaction(secretKey: ByteArray): ByteArray = ByteArray(0)

actual class SuggestedParams

actual class TransactionParams

actual fun TransactionParams.toSuggestedParams(addGenesisId: Boolean): SuggestedParams {
    TODO("Not yet implemented")
}
