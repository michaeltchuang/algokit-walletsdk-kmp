package com.michaeltchuang.walletsdk.core.encryption

import com.michaeltchuang.walletsdk.core.encryption.domain.manager.AESPlatformManager
import com.michaeltchuang.walletsdk.core.encryption.domain.manager.AndroidEncryptionManager
import org.koin.mp.KoinPlatform.getKoin

actual fun encryptByteArray(data: ByteArray): ByteArray = getKoin().get<AESPlatformManager>().encryptByteArray(data)

actual fun decryptByteArray(encryptedData: ByteArray): ByteArray = getKoin().get<AESPlatformManager>().decryptByteArray(encryptedData)

actual fun encryptString(data: String): String = getKoin().get<AESPlatformManager>().encryptString(data)

actual fun decryptString(encryptedData: String): String = getKoin().get<AESPlatformManager>().decryptString(encryptedData)

actual suspend fun initializeEncryptionManager() {
    getKoin().get<AndroidEncryptionManager>().initializeEncryptionManager()
}
