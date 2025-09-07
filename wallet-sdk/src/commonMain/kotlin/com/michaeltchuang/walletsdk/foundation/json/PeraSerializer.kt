/*
package com.michaeltchuang.walletsdk.foundation.json

import com.google.gson.Gson


internal class JsonSerializerImpl(val gson: Gson) : JsonSerializer {

    override fun toJson(payload: Any?): String {
        return gson.toJson(payload)
    }

    override fun <T> fromJson(json: String, type: Class<T>): T? {
        return try {
            gson.fromJson(json, type)
        } catch (exception: Exception) {
            null
        }
    }
}
*/
