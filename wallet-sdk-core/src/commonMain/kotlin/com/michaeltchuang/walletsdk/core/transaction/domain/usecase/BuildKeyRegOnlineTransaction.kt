package com.michaeltchuang.walletsdk.core.transaction.domain.usecase

import com.michaeltchuang.walletsdk.core.transaction.model.OnlineKeyRegTransactionPayload

interface BuildKeyRegOnlineTransaction {
    operator fun invoke(params: OnlineKeyRegTransactionPayload): ByteArray?
}
