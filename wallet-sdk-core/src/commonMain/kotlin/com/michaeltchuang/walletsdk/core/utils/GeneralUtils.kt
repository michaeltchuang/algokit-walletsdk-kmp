package com.michaeltchuang.walletsdk.core.utils

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.michaeltchuang.walletsdk.core.network.model.TransactionParams

const val MIN_FEE = 1000L
const val DATA_SIZE_FOR_MAX = 270
const val ROUND_THRESHOLD = 1000L

val minBalancePerAssetAsBigInteger = 100_000L

fun TransactionParams.getTxFee(signedTxData: ByteArray? = null): Long =
    ((signedTxData?.size ?: DATA_SIZE_FOR_MAX) * fee).coerceAtLeast(minFee ?: MIN_FEE)

infix fun BigInteger?.isLesserThan(other: BigInteger): Boolean = this?.compareTo(other) == -1

fun List<ByteArray>.flatten(): ByteArray {
    val totalSize = this.sumOf { it.size }
    val result = ByteArray(totalSize)
    var position = 0
    for (array in this) {
        array.copyInto(result, position)
        position += array.size
    }
    return result
}
