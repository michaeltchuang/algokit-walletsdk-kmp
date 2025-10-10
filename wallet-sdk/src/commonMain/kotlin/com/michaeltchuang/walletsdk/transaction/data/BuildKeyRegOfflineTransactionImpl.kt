package com.michaeltchuang.walletsdk.transaction.data

import com.michaeltchuang.walletsdk.transaction.domain.usecase.BuildKeyRegOfflineTransaction
import com.michaeltchuang.walletsdk.transaction.model.OfflineKeyRegTransactionPayload



internal class BuildKeyRegOfflineTransactionImpl () : BuildKeyRegOfflineTransaction {

    override fun invoke(payload: OfflineKeyRegTransactionPayload): ByteArray {
        return try {
            createTransaction(payload)
        } catch (e: Exception) {
            ByteArray(0)
        }
    }

    private fun createTransaction(payload: OfflineKeyRegTransactionPayload): ByteArray {
        return com.michaeltchuang.walletsdk.algosdk.createTransaction(payload)
    }
}
