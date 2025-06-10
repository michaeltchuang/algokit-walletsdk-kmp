/*
 * Copyright 2022-2025 Pera Wallet, LDA
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.michaeltchuang.walletsdk.algosdk.transaction.usecase

import com.michaeltchuang.walletsdk.algosdk.transaction.TransactionConstants.MIN_TXN_FEE
import java.math.BigInteger
import javax.inject.Inject

internal class CalculateTransactionFeeUseCase @Inject constructor() : CalculateTransactionFee {

    override fun invoke(fee: Long, minFee: Long?, signedTxn: ByteArray?): BigInteger {
        val calculatedFee = ((signedTxn?.size ?: DATA_SIZE_FOR_MAX) * fee)
        val safeMinFee = (minFee ?: MIN_TXN_FEE).toLong().coerceAtLeast(MIN_TXN_FEE.toLong())
        return calculatedFee.coerceAtLeast(safeMinFee).toBigInteger()
    }

    private companion object {
        const val DATA_SIZE_FOR_MAX = 270
    }
}
