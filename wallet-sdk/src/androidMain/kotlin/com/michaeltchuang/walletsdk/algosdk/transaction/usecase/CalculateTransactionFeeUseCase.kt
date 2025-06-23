

package com.michaeltchuang.walletsdk.algosdk.transaction.usecase

import com.michaeltchuang.walletsdk.algosdk.transaction.TransactionConstants.MIN_TXN_FEE
import java.math.BigInteger
import javax.inject.Inject

internal class CalculateTransactionFeeUseCase
    @Inject
    constructor() : CalculateTransactionFee {
        override fun invoke(
            fee: Long,
            minFee: Long?,
            signedTxn: ByteArray?,
        ): BigInteger {
            val calculatedFee = ((signedTxn?.size ?: DATA_SIZE_FOR_MAX) * fee)
            val safeMinFee = (minFee ?: MIN_TXN_FEE).toLong().coerceAtLeast(MIN_TXN_FEE.toLong())
            return calculatedFee.coerceAtLeast(safeMinFee).toBigInteger()
        }

        private companion object {
            const val DATA_SIZE_FOR_MAX = 270
        }
    }
