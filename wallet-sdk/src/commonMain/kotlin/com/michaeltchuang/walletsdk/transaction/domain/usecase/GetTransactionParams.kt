package com.michaeltchuang.walletsdk.transaction.domain.usecase

import com.michaeltchuang.walletsdk.network.model.TransactionParams
import com.michaeltchuang.walletsdk.utils.Result

fun interface GetTransactionParams {
    suspend operator fun invoke(): Result<TransactionParams>
}
