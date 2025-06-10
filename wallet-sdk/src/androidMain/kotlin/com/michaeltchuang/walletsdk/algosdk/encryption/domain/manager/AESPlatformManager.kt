 

package com.michaeltchuang.walletsdk.algosdk.encryption.domain.manager

interface AESPlatformManager {
    fun encryptByteArray(data: ByteArray): ByteArray
    fun decryptByteArray(encryptedData: ByteArray): ByteArray
    fun encryptString(data: String): String
    fun decryptString(encryptedData: String): String
}
