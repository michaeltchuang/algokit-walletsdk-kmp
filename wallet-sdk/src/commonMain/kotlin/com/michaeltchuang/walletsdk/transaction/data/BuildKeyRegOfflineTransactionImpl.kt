package com.michaeltchuang.walletsdk.transaction.data

import com.michaeltchuang.walletsdk.foundation.utils.Log
import com.michaeltchuang.walletsdk.transaction.domain.usecase.BuildKeyRegOfflineTransaction
import com.michaeltchuang.walletsdk.transaction.model.OfflineKeyRegTransactionPayload

internal class BuildKeyRegOfflineTransactionImpl : BuildKeyRegOfflineTransaction {
    override fun invoke(payload: OfflineKeyRegTransactionPayload): ByteArray =
        try {
            createTransaction(payload)
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Unknown error")
            ByteArray(0)
        }

    private fun createTransaction(payload: OfflineKeyRegTransactionPayload): ByteArray =
        com.michaeltchuang.walletsdk.algosdk
            .createTransaction(payload)

    companion object {
        const val TAG = "BuildKeyRegOfflineTransactionImpl"
    }
}
