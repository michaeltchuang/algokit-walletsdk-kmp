package com.michaeltchuang.walletsdk.core.transaction.domain.usecase

import com.michaeltchuang.walletsdk.core.foundation.utils.Result
import com.michaeltchuang.walletsdk.core.network.model.TransactionParams

fun interface GetTransactionParams {
    suspend operator fun invoke(): Result<TransactionParams>
}
