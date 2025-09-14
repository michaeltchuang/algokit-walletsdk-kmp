package com.michaeltchuang.walletsdk.foundation.json

import kotlin.reflect.KType


interface JsonSerializer {
    fun <T> toJson(payload: T, type: KType): String
    fun <T> fromJson(jsonString: String, type: KType): T?
}