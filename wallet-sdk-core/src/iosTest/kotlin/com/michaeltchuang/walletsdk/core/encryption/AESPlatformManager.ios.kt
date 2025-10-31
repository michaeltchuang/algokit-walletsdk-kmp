package com.michaeltchuang.walletsdk.core.encryption

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * Test implementation of encryption functions for iOS tests.
 * This provides simple XOR-based encryption that doesn't require iOS Keychain or Secure Enclave.
 */

private const val TEST_KEY = "TEST_ENCRYPTION_KEY_FOR_UNIT_TESTS_1234567890"

@OptIn(ExperimentalEncodingApi::class)
actual fun encryptByteArray(data: ByteArray): ByteArray {
    // Simple XOR encryption with a fixed key for testing
    val keyBytes = TEST_KEY.encodeToByteArray()
    return data.mapIndexed { index, byte ->
        (byte.toInt() xor keyBytes[index % keyBytes.size].toInt()).toByte()
    }.toByteArray()
}

@OptIn(ExperimentalEncodingApi::class)
actual fun decryptByteArray(encryptedData: ByteArray): ByteArray {
    // XOR encryption is symmetric, so decryption is the same as encryption
    return encryptByteArray(encryptedData)
}

@OptIn(ExperimentalEncodingApi::class)
actual fun encryptString(data: String): String {
    val dataBytes = data.encodeToByteArray()
    val encrypted = encryptByteArray(dataBytes)
    return Base64.encode(encrypted)
}

@OptIn(ExperimentalEncodingApi::class)
actual fun decryptString(encryptedData: String): String {
    val encryptedBytes = Base64.decode(encryptedData)
    val decrypted = decryptByteArray(encryptedBytes)
    return decrypted.decodeToString()
}

actual suspend fun initializeEncryptionManager() {
    // No initialization needed for test implementation
}
