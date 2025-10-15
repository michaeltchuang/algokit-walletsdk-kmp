package com.michaeltchuang.walletsdk.core.encryption.domain.utils

fun ByteArray.clearFromMemory(): ByteArray {
    // Overwrite the byte array contents with zeros
    this.fill(0)
    return ByteArray(0)
}
