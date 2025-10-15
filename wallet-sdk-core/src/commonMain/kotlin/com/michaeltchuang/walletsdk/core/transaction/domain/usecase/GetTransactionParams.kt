package com.michaeltchuang.walletsdk.core.transaction.domain.usecase

import com.michaeltchuang.walletsdk.core.network.model.TransactionParams
import com.michaeltchuang.walletsdk.utils.Result

fun interface GetTransactionParams {
    suspend operator fun invoke(): Result<TransactionParams>
}
