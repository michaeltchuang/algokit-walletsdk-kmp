package com.michaeltchuang.walletsdk.core.encryption

import AlgorandIosSdk.IosEncryptionManager
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.create
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
private val encryptionManager: IosEncryptionManager by lazy { IosEncryptionManager() }

@OptIn(ExperimentalForeignApi::class)
actual fun encryptByteArray(data: ByteArray): ByteArray {
    val nsData = data.toNSData()
    val encryptedNSData = encryptionManager.encryptByteArray(nsData)
    return encryptedNSData.toByteArray()
}

@OptIn(ExperimentalForeignApi::class)
actual fun decryptByteArray(encryptedData: ByteArray): ByteArray {
    val nsData = encryptedData.toNSData()
    val decryptedNSData = encryptionManager.decryptByteArray(nsData)
    return decryptedNSData.toByteArray()
}

@OptIn(ExperimentalForeignApi::class)
actual fun encryptString(data: String): String {
    return encryptionManager.encryptString(data)
}

@OptIn(ExperimentalForeignApi::class)
actual fun decryptString(encryptedData: String): String {
    return encryptionManager.decryptString(encryptedData)
}

@OptIn(ExperimentalForeignApi::class)
actual suspend fun initializeEncryptionManager() {
    encryptionManager.initializeEncryptionManager()
}

// Helper extensions to convert between ByteArray and NSData
@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
private fun ByteArray.toNSData(): NSData {
    if (this.isEmpty()) {
        return NSData()
    }

    return this.usePinned { pinned ->
        NSData.create(
            bytes = pinned.addressOf(0),
            length = this.size.toULong(),
        )
    }
}

@OptIn(ExperimentalForeignApi::class)
private fun NSData.toByteArray(): ByteArray {
    val length = this.length.toInt()
    if (length == 0) {
        return ByteArray(0)
    }

    return ByteArray(length).apply {
        usePinned { pinned ->
            memcpy(pinned.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
        }
    }
}
