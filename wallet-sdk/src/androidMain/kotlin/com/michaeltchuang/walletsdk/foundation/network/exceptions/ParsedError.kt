package com.michaeltchuang.walletsdk.foundation.network.exceptions

data class ParsedError(
    val keyErrorMessageMap: Map<String, List<String>>,
    val message: String,
    val responseCode: Int,
)
