package com.michaeltchuang.walletsdk.algosdk

import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import kotlin.random.Random

actual fun createAlgo25Account(): Algo25Account? {
    return Algo25Account(generateRandomAddress(), ByteArray(25))
}

actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray) {}


private fun generateRandomAddress(): String {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567" // Base32 characters
    val addressLength = 58

    return (1..addressLength)
        .map { alphabet[Random.nextInt(alphabet.length)] }
        .joinToString("")
}