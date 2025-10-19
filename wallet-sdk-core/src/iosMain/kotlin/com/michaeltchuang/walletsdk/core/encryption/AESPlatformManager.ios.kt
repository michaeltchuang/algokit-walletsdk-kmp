package com.michaeltchuang.walletsdk.core.encryption

import AlgorandIosSdk.IosEncryptionManager
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalForeignApi::class)
private val encryptionManager: IosEncryptionManager by lazy { IosEncryptionManager() }

class EncryptionException(message: String, cause: Throwable? = null) : Exception(message, cause)

@OptIn(ExperimentalForeignApi::class, ExperimentalEncodingApi::class)
actual fun encryptByteArray(data: ByteArray): ByteArray {
    val dataBase64 = Base64.encode(data)
    val encryptedBase64 = encryptionManager.encryptString(dataBase64)

    if (encryptedBase64.isEmpty()) {
        throw EncryptionException("Encryption failed: returned empty string")
    }

    return try {
        Base64.decode(encryptedBase64)
    } catch (e: Exception) {
        throw EncryptionException("Failed to decode encrypted data", e)
    }
}

@OptIn(ExperimentalForeignApi::class, ExperimentalEncodingApi::class)
actual fun decryptByteArray(encryptedData: ByteArray): ByteArray {
    val encryptedBase64 = Base64.encode(encryptedData)
    val decryptedBase64 = encryptionManager.decryptString(encryptedBase64)

    if (decryptedBase64.isEmpty()) {
        throw EncryptionException("Decryption failed: returned empty string")
    }
    return try {
        Base64.decode(decryptedBase64)
    } catch (e: Exception) {
        throw EncryptionException("Failed to decode decrypted data", e)
    }
}

@OptIn(ExperimentalForeignApi::class)
actual fun encryptString(data: String): String {
    val encrypted = encryptionManager.encryptString(data)

    if (encrypted.isEmpty()) {
        throw EncryptionException("String encryption failed: returned empty string")
    }

    return encrypted
}

@OptIn(ExperimentalForeignApi::class)
actual fun decryptString(encryptedData: String): String {
    val decrypted = encryptionManager.decryptString(encryptedData)

    if (decrypted.isEmpty() && encryptedData.isNotEmpty()) {
        throw EncryptionException("String decryption failed: returned empty string for non-empty input")
    }

    return decrypted
}

@OptIn(ExperimentalForeignApi::class)
actual suspend fun initializeEncryptionManager() {
    try {
        encryptionManager.initializeEncryptionManager()
    } catch (e: Exception) {
        throw EncryptionException("Failed to initialize encryption manager", e)
    }
}
