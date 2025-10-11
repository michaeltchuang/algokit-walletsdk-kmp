package com.michaeltchuang.walletsdk.transaction.domain.usecase

import com.michaeltchuang.walletsdk.transaction.model.OfflineKeyRegTransactionPayload

interface BuildKeyRegOfflineTransaction {
    operator fun invoke(payload: OfflineKeyRegTransactionPayload): ByteArray?
}
