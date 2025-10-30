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

fun String.toAlgoAmount(): String {
    if (this.isEmpty()) return "0"
    if (this == "0") return "0"

    return try {
        // Split into integer and decimal parts
        val parts = this.split(".")
        val intPart = parts[0]
        val decPart = parts.getOrNull(1)

        // Validate that intPart contains only digits
        if (intPart.any { !it.isDigit() }) {
            return this // Return original if not a valid number
        }

        // Format integer part with thousands separators using string manipulation
        val formattedIntPart = intPart
            .reversed()
            .chunked(3)
            .joinToString(",")
            .reversed()

        // Only add decimal part if user entered a decimal point
        if (decPart != null) {
            // Handle decimal part according to #,##0.00#### pattern
            val formattedDecPart = when {
                decPart.isEmpty() -> "" // User just entered "." so show nothing after
                decPart.length == 1 -> decPart // Show single decimal digit
                decPart.length <= 6 -> decPart.trimEnd('0').let { trimmed ->
                    trimmed.ifEmpty { "" }
                }
                else -> decPart.take(6).trimEnd('0').let { trimmed ->
                    trimmed.ifEmpty { "" }
                }
            }

            return if (formattedDecPart.isEmpty() && decPart.isEmpty()) {
                "$formattedIntPart." // User entered "123." so show "123."
            } else if (formattedDecPart.isEmpty()) {
                formattedIntPart // User entered "123.00" -> show "123"
            } else {
                "$formattedIntPart.$formattedDecPart"
            }
        } else {
            // Pure integer, no decimal point entered
            return formattedIntPart
        }
    } catch (e: Exception) {
        this // Return original if parsing fails
    }
}
