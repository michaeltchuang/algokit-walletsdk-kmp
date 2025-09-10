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
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants
import fr.acinq.bitcoin.MnemonicCode
import io.ktor.utils.io.core.toByteArray
import kotlin.random.Random
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
val contentFromSwift = xHdWalletApiBridge().toMD5WithValue(
    value = WalletSdkConstants.SAMPLE_HD_MNEMONIC
)

actual fun createAlgo25Account(): Algo25Account? {
    return Algo25Account(generateRandomAddress(), ByteArray(25))
}

actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray) {}


actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet {
    return getBit39Wallet()
}
actual fun createBip39Wallet(): Bip39Wallet {
    return getBit39Wallet()
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
                address = "ASDFGHJKLASDFGHJKL",
                index = HdKeyAddressIndex(0),
                privateKey = ByteArray(0),
                publicKey = ByteArray(0),
                derivationType = HdKeyAddressDerivationType.Peikert
            )
        }

        override fun generateAddressLite(index: HdKeyAddressIndex): HdKeyAddressLite {
            return HdKeyAddressLite(
                address = "ASDFGHJKLASDFGHJKL",
                index = HdKeyAddressIndex(0),
            )
        }

        override fun invalidate() {}
    }
}

private fun getMnemonicFromEntropy(entropy: ByteArray): String {
        val mnemonic = MnemonicCode.toMnemonics(entropy)
        return mnemonic.toString()
    }

private fun generateRandomAddress(): String {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567" // Base32 characters
    val addressLength = 58

    return (1..addressLength)
        .map { alphabet[Random.nextInt(alphabet.length)] }
        .joinToString("")
}

private fun generate25WordMnemonic(): String {
    return """
        abandon abandon abandon abandon abandon 
        abandon abandon abandon abandon abandon 
        abandon abandon abandon abandon abandon 
        abandon abandon abandon abandon abandon 
        abandon abandon abandon abandon abandon
        """.trimIndent().lowercase()
}
