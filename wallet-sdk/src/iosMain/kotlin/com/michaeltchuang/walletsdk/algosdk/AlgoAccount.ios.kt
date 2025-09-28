package com.michaeltchuang.walletsdk.algosdk

import AlgorandXhdIosSdk.xHdWalletApiBridge
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Bip39Entropy
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Bip39Mnemonic
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Bip39Seed
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddress
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddressDerivationType
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddressIndex
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddressLite
import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.Bip39Wallet
import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import io.ktor.util.decodeBase64Bytes
import io.ktor.utils.io.core.toByteArray
import kotlin.random.Random
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.*
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData {
    return this.usePinned { pinned ->
        NSData.dataWithBytes(
            bytes = pinned.addressOf(0),
            length = this.size.toULong()
        )
    }
}

private fun String.fromBase64ToByteArray(): ByteArray {
    return try {
        this.decodeBase64Bytes()
    } catch (e: Exception) {
        println("Ktor Base64 decode error: ${e.message}")
        ByteArray(0)
    }
}

@OptIn(ExperimentalForeignApi::class)
actual fun recoverAlgo25Account(mnemonic: String): Algo25Account? {
    val secretKey = xHdWalletApiBridge().getAlgo25SecretKeyWithMnemonic(
        mnemonic = mnemonic
    )
    val address = xHdWalletApiBridge().generateAddressFromSKWithSecretKey(
        secretKey = secretKey
    )
    return Algo25Account(address, secretKey.fromBase64ToByteArray())
}
@OptIn(ExperimentalForeignApi::class)
actual fun createAlgo25Account(): Algo25Account? {
    val secretKey = xHdWalletApiBridge().getAlgo25SecretKeyWithMnemonic(
        mnemonic = null
    )
    val address = xHdWalletApiBridge().generateAddressFromSKWithSecretKey(
        secretKey = secretKey
    )
    return Algo25Account(address, secretKey.fromBase64ToByteArray())
}

@OptIn(ExperimentalForeignApi::class)
actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray): String? {
    var mnemonic: String? = null
    try {
        mnemonic =
            xHdWalletApiBridge().getAlgo25MnemonicFromSecretKeyWithSecretKey(secretKey.toNSData())
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
    return mnemonic
}


actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet {
    return getBit39Wallet()
}
actual fun createBip39Wallet(): Bip39Wallet {
    return getBit39Wallet()
}

actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray?{
    return AlgoKitBip39.getSeedFromEntropy(entropy)
}

private fun getBit39Wallet(): Bip39Wallet {

    return object : Bip39Wallet {
        override fun getEntropy(): Bip39Entropy {
            return Bip39Entropy(
                AlgoKitBip39.getEntropyFromMnemonic(AlgoKitBip39.generate24WordMnemonic())
                    .toByteArray()
            )
        }

        override fun getSeed(): Bip39Seed {
            return Bip39Seed(
                AlgoKitBip39.getSeedFromEntropy(
                    AlgoKitBip39.getEntropyFromMnemonic(AlgoKitBip39.generate24WordMnemonic())
                        .toByteArray()
                )
            )
        }

        override fun getMnemonic(): Bip39Mnemonic {
            return Bip39Mnemonic(emptyList())
        }

        override fun generateAddress(index: HdKeyAddressIndex): HdKeyAddress {
            return HdKeyAddress(
                address = generateRandomAddress(),
                index = HdKeyAddressIndex(0),
                privateKey = ByteArray(32),
                publicKey = ByteArray(32),
                derivationType = HdKeyAddressDerivationType.Peikert
            )
        }

        override fun generateAddressLite(index: HdKeyAddressIndex): HdKeyAddressLite {
            return HdKeyAddressLite(
                address = generateRandomAddress(),
                index = HdKeyAddressIndex(0),
            )
        }

        override fun invalidate() {}
    }
}

private fun generateRandomAddress(): String {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567" // Base32 characters
    val addressLength = 58

    return (1..addressLength)
        .map { alphabet[Random.nextInt(alphabet.length)] }
        .joinToString("")
}
