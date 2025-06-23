package com.michaeltchuang.walletsdk.foundation.json

interface JsonSerializer {
    fun toJson(payload: Any?): String

    fun <T> fromJson(
        json: String,
        type: Class<T>,
    ): T?
}
