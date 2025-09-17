package com.michaeltchuang.walletsdk.algosdk.transaction.sdk

import com.algorand.algosdk.account.Account
import com.algorand.algosdk.sdk.Sdk
import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import com.michaeltchuang.walletsdk.encryption.domain.utils.clearFromMemory
import org.bouncycastle.jce.provider.BouncyCastleProvider
import java.security.NoSuchAlgorithmException
import java.security.Security

internal class AlgoAccountSdkImpl : AlgoAccountSdk {
    init {
        Security.removeProvider("BC")
        Security.insertProviderAt(BouncyCastleProvider(), 0)
    }

    override fun createAlgo25Account(): Algo25Account? {
        return try {
            var secretKey = Sdk.generateSK()
            val output =
                Algo25Account(
                    address = Sdk.generateAddressFromSK(secretKey),
                    secretKey = secretKey.copyOf(),
                )
            secretKey.clearFromMemory()
            output
        } catch (e: Exception) {
            null
        }
    }

    override fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray): String? {
        return try {
            Account(secretKey).toMnemonic()
        } catch (e: NoSuchAlgorithmException) {
            null
        }
    }

    override fun recoverAlgo25Account(mnemonic: String): Algo25Account? {
        return try {
            var secretKey = Sdk.mnemonicToPrivateKey(mnemonic)

            val output =
                Algo25Account(
                    address = Sdk.generateAddressFromSK(secretKey),
                    secretKey = secretKey.copyOf(),
                )
            secretKey.clearFromMemory()
            output
        } catch (e: Exception) {
            null
        }
    }
}
