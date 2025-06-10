 

package com.michaeltchuang.walletsdk.algosdk.encryption.domain.usecase

import com.michaeltchuang.walletsdk.algosdk.foundation.AlgoKitResult
import javax.crypto.SecretKey

interface AndroidEncryptionManager {
    fun getSecretKey(): SecretKey
    suspend fun initializeEncryptionManager()
    suspend fun shouldMigrateToStrongBox(): Boolean
    suspend fun migrateToStrongBox(): AlgoKitResult<Boolean>
}
