package com.michaeltchuang.walletsdk.utils

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

fun ByteArray.clearFromMemory(): ByteArray {
    // Overwrite the byte array contents with zeros
    this.fill(0)
    return ByteArray(0)
}

val jsonConfig =
    Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

inline fun <reified T> SavedStateHandle.setObject(value: T) {
    this["data"] = jsonConfig.encodeToString(serializer<T>(), value)
}

inline fun <reified T> SavedStateHandle.getObject(): T? {
    val json = this.get<String>("data") ?: return null
    return jsonConfig.decodeFromString(serializer<T>(), json)
}

inline fun <reified T> NavController.navigateWithArgument(
    route: String,
    bundle: T,
) {
    setData(bundle)
    navigate(route)
}

inline fun <reified T> NavController.setData(data: T) {
    currentBackStackEntry
        ?.savedStateHandle
        ?.setObject(data)
}

inline fun <reified T> NavController.getData(): T? =
    this.previousBackStackEntry
        ?.savedStateHandle
        ?.getObject()

fun String.formatAmount(): String =
    try {
        val microalgos = BigDecimal.parseString(this)
        val divisor = BigDecimal.parseString("1000000")
        val algos = microalgos.divide(divisor)

        // Round to 6 decimal places
        val rounded =
            algos.roundToDigitPosition(
                digitPosition = 6,
                roundingMode = RoundingMode.ROUND_HALF_AWAY_FROM_ZERO,
            )

        // Format with exactly 6 decimal places
        val str = rounded.toStringExpanded()
        val parts = str.split(".")
        val intPart = parts[0]
        val decPart = parts.getOrNull(1)?.take(6)?.padEnd(6, '0') ?: "000000"

        "$intPart.$decPart"
    } catch (e: Exception) {
        this
    }
