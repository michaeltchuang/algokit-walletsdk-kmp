package com.michaeltchuang.walletsdk.core.transaction.domain.usecase

import com.michaeltchuang.walletsdk.core.transaction.model.OfflineKeyRegTransactionPayload

interface BuildKeyRegOfflineTransaction {
    operator fun invoke(payload: OfflineKeyRegTransactionPayload): ByteArray?
}
