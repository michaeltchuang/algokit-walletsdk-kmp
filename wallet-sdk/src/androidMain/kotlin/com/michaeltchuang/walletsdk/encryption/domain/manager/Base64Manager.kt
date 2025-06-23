package com.michaeltchuang.walletsdk.encryption.domain.manager

interface Base64Manager {
    fun encode(byteArray: ByteArray): String
    fun decode(value: String): ByteArray
    fun decode(value: String, flags: Int): ByteArray
}
