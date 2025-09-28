package com.michaeltchuang.walletsdk.utils

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

fun ByteArray.clearFromMemory(): ByteArray {
    // Overwrite the byte array contents with zeros
    this.fill(0)
    return ByteArray(0)
}

val jsonConfig = Json {
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

inline fun <reified T> NavController.navigateWithArgument(route: String, bundle: T) {
    setData(bundle)
    navigate(route)
}

inline fun <reified T> NavController.setData(data: T) {
    currentBackStackEntry
        ?.savedStateHandle?.setObject(data )

}

inline fun <reified T> NavController.getData(): T? {
    return this.previousBackStackEntry
        ?.savedStateHandle
        ?.getObject()
}
