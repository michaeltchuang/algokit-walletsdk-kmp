package com.michaeltchuang.walletsdk.foundation.utils

import com.michaeltchuang.walletsdk.network.model.TransactionParams

actual fun ByteArray.signTransaction(secretKey: ByteArray): ByteArray = ByteArray(0)

actual class SuggestedParams

actual class TransactionParams

actual fun TransactionParams.toSuggestedParams(addGenesisId: Boolean): SuggestedParams {
    TODO("iOS SDK not yet integrated - implement this when SDK is added")
    // Or return a dummy instance:
    // return SuggestedParams()
}
