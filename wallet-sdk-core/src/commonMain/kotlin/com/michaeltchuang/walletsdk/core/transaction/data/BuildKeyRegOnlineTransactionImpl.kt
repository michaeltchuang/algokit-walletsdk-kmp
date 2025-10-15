package com.michaeltchuang.walletsdk.core.transaction.data

import com.michaeltchuang.walletsdk.core.transaction.domain.usecase.BuildKeyRegOnlineTransaction
import com.michaeltchuang.walletsdk.core.transaction.model.OnlineKeyRegTransactionPayload

internal class BuildKeyRegOnlineTransactionImpl : BuildKeyRegOnlineTransaction {
    override fun invoke(params: OnlineKeyRegTransactionPayload): ByteArray? =
        try {
            createTransaction(params)
        } catch (e: Exception) {
            null
        }

    private fun createTransaction(payload: OnlineKeyRegTransactionPayload): ByteArray =
        com.michaeltchuang.walletsdk.core.algosdk
            .createTransaction(payload)
}
