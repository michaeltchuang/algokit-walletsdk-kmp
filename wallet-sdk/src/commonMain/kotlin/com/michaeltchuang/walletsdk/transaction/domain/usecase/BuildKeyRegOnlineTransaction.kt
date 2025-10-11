package com.michaeltchuang.walletsdk.transaction.domain.usecase

import com.michaeltchuang.walletsdk.transaction.model.OnlineKeyRegTransactionPayload

interface BuildKeyRegOnlineTransaction {
    operator fun invoke(params: OnlineKeyRegTransactionPayload): ByteArray?
}
