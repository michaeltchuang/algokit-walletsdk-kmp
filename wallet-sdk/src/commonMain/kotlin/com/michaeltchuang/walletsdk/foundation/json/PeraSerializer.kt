package com.michaeltchuang.walletsdk.foundation.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import kotlin.reflect.KType

class JsonSerializerImpl : JsonSerializer {
    private val json =
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }

    override fun <T> toJson(
        payload: T,
        type: KType,
    ): String {
        val serializer = serializer(type)
        return json.encodeToString(serializer, payload)
    }

    override fun <T> fromJson(
        jsonString: String,
        type: KType,
    ): T? =
        try {
            val serializer = serializer(type)
            @Suppress("UNCHECKED_CAST")
            json.decodeFromString(serializer, jsonString) as T
        } catch (e: Exception) {
            null
        }
}
