package com.michaeltchuang.walletsdk.core.encryption.domain.manager

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import com.michaeltchuang.walletsdk.core.encryption.domain.usecase.GetStrongBoxUsedCheck
import com.michaeltchuang.walletsdk.core.encryption.domain.usecase.SaveStrongBoxUsedCheck
import com.michaeltchuang.walletsdk.core.foundation.AlgoKitResult
import java.security.KeyStore
import java.security.ProviderException
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

internal class AndroidEncryptionManagerImpl(
    private val getStrongBoxUsedCheck: GetStrongBoxUsedCheck,
    private val saveStrongBoxUsedCheck: SaveStrongBoxUsedCheck,
) : AndroidEncryptionManager {

    override suspend fun initializeEncryptionManager() {
        generateKeyIfNeeded()
    }

    /**
     * Checks if the current key should be migrated to StrongBox
     * @return true if migration is recommended
     */
    override suspend fun shouldMigrateToStrongBox(): Boolean {
        if (getStrongBoxUsedCheck.invoke()) {
            return false
        }

        try {
            initializeStrongBoxEncryption()
            return true
        } catch (e: Exception) {
            // StrongBox still not available
            return false
        } finally {
            try {
                // Clean up the test key if it was created
                cleanUpStrongBoxTestAlias()
            } catch (e: Exception) {
                Log.e(TAG, "Error cleaning up test key", e)
            }
        }
    }

    private fun initializeStrongBoxEncryption() {
        val keyGenerator = getKeyGenerator()
        val testBuilder = getKeyGenParameterSpecBuilder(STRONG_BOX_TEST_ALIAS)
            .setIsStrongBoxBacked(true)
        keyGenerator.init(testBuilder.build())
    }

    private fun cleanUpStrongBoxTestAlias() {
        val keyStore = getKeyStore()
        if (keyStore.containsAlias(STRONG_BOX_TEST_ALIAS)) {
            keyStore.deleteEntry(STRONG_BOX_TEST_ALIAS)
        }
    }

    /**
     * Migrates encryption keys to StrongBox
     * @return true if migration was successful
     */
    override suspend fun migrateToStrongBox(): AlgoKitResult<Boolean> {
        if (!shouldMigrateToStrongBox()) {
            return AlgoKitResult.Success(false)
        }

        try {
            val keyStore = getKeyStore()
            val originalKeyExists = keyStore.containsAlias(KEY_ALIAS)
            if (!originalKeyExists) {
                Log.d(TAG, "No key to migrate to StrongBox")
                return AlgoKitResult.Success(false)
            }

            val keyGenerator = getKeyGenerator()
            createKey(keyGenerator, STRONG_BOX_ALIAS, useStrongBox = true)

            saveStrongBoxUsedCheck.invoke(true)

            // migrate all secret keys in db tables
            // not completely implemented yet so return false

            return AlgoKitResult.Success(false)
        } catch (e: Exception) {
            Log.e(TAG, "Failed to migrate to StrongBox", e)
            return AlgoKitResult.Error(e)
        }
    }

    private fun getKeyStore(): KeyStore {
        return KeyStore.getInstance(ANDROID_KEYSTORE).apply {
            load(null)
        }
    }

    private fun getKeyGenerator(): KeyGenerator {
        return KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
    }

    private suspend fun generateKeyIfNeeded() {
        val keyStore = getKeyStore()

        if (!keyStore.containsAlias(KEY_ALIAS) && !keyStore.containsAlias(STRONG_BOX_ALIAS)) {
            val keyGenerator = getKeyGenerator()

            try {
                // Try to create StrongBox-backed key first
                createKey(keyGenerator, STRONG_BOX_ALIAS, useStrongBox = true)
                saveStrongBoxUsedCheck.invoke(true)
                Log.d(TAG, "StrongBox key generated successfully")
            } catch (e: ProviderException) {
                // Fall back to software-backed key
                Log.d(TAG, "StrongBox not available, falling back to software-backed key", e)
                createKey(keyGenerator, KEY_ALIAS, useStrongBox = false)
                saveStrongBoxUsedCheck.invoke(false)
                Log.d(TAG, "Software-backed key generated successfully")
            }
        }
    }

    private fun getKeyGenParameterSpecBuilder(alias: String): KeyGenParameterSpec.Builder {
        return KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setKeySize(ENCRYPTION_KEY_SIZE_IN_BITS)
    }

    private fun createKey(keyGenerator: KeyGenerator, alias: String, useStrongBox: Boolean) {
        val builder = getKeyGenParameterSpecBuilder(alias)

        if (useStrongBox) {
            builder.setIsStrongBoxBacked(true)
        }

        keyGenerator.init(builder.build())
        keyGenerator.generateKey()
    }

    override fun getSecretKey(): SecretKey {
        val keyStore = getKeyStore()
        return if (keyStore.containsAlias(STRONG_BOX_ALIAS)) {
            keyStore.getKey(STRONG_BOX_ALIAS, null) as SecretKey
        } else {
            keyStore.getKey(KEY_ALIAS, null) as SecretKey
        }
    }

    private companion object {
        const val ANDROID_KEYSTORE = "AndroidKeyStore" // this value should not change
        const val KEY_ALIAS = "PeraAESKey"
        const val STRONG_BOX_ALIAS = "${KEY_ALIAS}_strongbox"
        const val STRONG_BOX_TEST_ALIAS = "StrongBoxTest"
        const val ENCRYPTION_KEY_SIZE_IN_BITS = 256
        val TAG: String = AndroidEncryptionManager::class.java.simpleName
    }
}
