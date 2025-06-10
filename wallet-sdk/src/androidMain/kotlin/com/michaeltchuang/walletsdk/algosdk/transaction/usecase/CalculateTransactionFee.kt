 

package com.michaeltchuang.walletsdk.algosdk.transaction.usecase

import java.math.BigInteger

fun interface CalculateTransactionFee {
    operator fun invoke(fee: Long, minFee: Long?, signedTxn: ByteArray?): BigInteger
}
