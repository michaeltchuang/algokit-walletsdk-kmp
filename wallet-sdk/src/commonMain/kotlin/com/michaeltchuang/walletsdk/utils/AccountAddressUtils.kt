package com.michaeltchuang.walletsdk.utils

private const val SHORTENED_ADDRESS_LETTER_COUNT = 4

fun String?.toShortenedAddress(): String =
    if (!this.isNullOrBlank()) {
        "${take(SHORTENED_ADDRESS_LETTER_COUNT)}...${takeLast(SHORTENED_ADDRESS_LETTER_COUNT)}"
    } else {
        ""
    }
