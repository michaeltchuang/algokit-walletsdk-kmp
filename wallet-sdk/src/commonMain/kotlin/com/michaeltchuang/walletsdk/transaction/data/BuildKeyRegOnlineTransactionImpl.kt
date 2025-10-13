package com.michaeltchuang.walletsdk.transaction.data

import com.michaeltchuang.walletsdk.transaction.domain.usecase.BuildKeyRegOnlineTransaction
import com.michaeltchuang.walletsdk.transaction.model.OnlineKeyRegTransactionPayload

internal class BuildKeyRegOnlineTransactionImpl : BuildKeyRegOnlineTransaction {
    override fun invoke(params: OnlineKeyRegTransactionPayload): ByteArray? =
        try {
            createTransaction(params)
        } catch (e: Exception) {
            null
        }

    private fun createTransaction(payload: OnlineKeyRegTransactionPayload): ByteArray =
        com.michaeltchuang.walletsdk.algosdk
            .createTransaction(payload)
}
