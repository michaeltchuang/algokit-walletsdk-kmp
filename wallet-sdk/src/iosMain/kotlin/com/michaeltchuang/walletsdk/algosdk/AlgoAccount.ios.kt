package com.michaeltchuang.walletsdk.algosdk

import AlgorandIosSdk.spmAlgoApiBridge
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Bip39Entropy
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Bip39Mnemonic
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Bip39Seed
import com.michaeltchuang.walletsdk.algosdk.bip39.model.Falcon24
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddress
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddressDerivationType
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddressIndex
import com.michaeltchuang.walletsdk.algosdk.bip39.model.HdKeyAddressLite
import com.michaeltchuang.walletsdk.algosdk.bip39.sdk.Bip39Wallet
import com.michaeltchuang.walletsdk.algosdk.domain.model.Algo25Account
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants
import io.ktor.util.decodeBase64Bytes
import io.ktor.utils.io.core.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import kotlin.random.Random

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData =
    this.usePinned { pinned ->
        NSData.dataWithBytes(
            bytes = pinned.addressOf(0),
            length = this.size.toULong(),
        )
    }

private fun String.fromBase64ToByteArray(): ByteArray =
    try {
        this.decodeBase64Bytes()
    } catch (e: Exception) {
        println("Ktor Base64 decode error: ${e.message}")
        ByteArray(0)
    }

@OptIn(ExperimentalForeignApi::class)
actual fun recoverAlgo25Account(mnemonic: String): Algo25Account? {
    val secretKey =
        spmAlgoApiBridge().getAlgo25SecretKeyWithMnemonic(
            mnemonic = mnemonic,
        )
    val address =
        spmAlgoApiBridge().generateAddressFromSKWithSecretKey(
            secretKey = secretKey,
        )
    return Algo25Account(address, secretKey.fromBase64ToByteArray())
}

@OptIn(ExperimentalForeignApi::class)
actual fun createAlgo25Account(): Algo25Account? {
    val secretKey =
        spmAlgoApiBridge().getAlgo25SecretKeyWithMnemonic(
            mnemonic = null,
        )
    val address =
        spmAlgoApiBridge().generateAddressFromSKWithSecretKey(
            secretKey = secretKey,
        )
    return Algo25Account(address, secretKey.fromBase64ToByteArray())
}

@OptIn(ExperimentalForeignApi::class)
actual fun getMnemonicFromAlgo25SecretKey(secretKey: ByteArray): String? {
    var mnemonic: String? = null
    try {
        mnemonic =
            spmAlgoApiBridge().getAlgo25MnemonicFromSecretKeyWithSecretKey(secretKey.toNSData())
    } catch (e: Exception) {
        println("ERROR: ${e.message}")
    }
    return mnemonic
}

actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet = getBit39Wallet()

actual fun createBip39Wallet(): Bip39Wallet = getBit39Wallet()

actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray? = AlgoKitBip39.getSeedFromEntropy(entropy)

@OptIn(ExperimentalForeignApi::class)
private fun getBit39Wallet(): Bip39Wallet =
    object : Bip39Wallet {
        override fun getEntropy(): Bip39Entropy =
            Bip39Entropy(
                AlgoKitBip39
                    .getEntropyFromMnemonic(AlgoKitBip39.generate24WordMnemonic())
                    .toByteArray(),
            )

        override fun getSeed(): Bip39Seed =
            Bip39Seed(
                AlgoKitBip39.getSeedFromEntropy(
                    AlgoKitBip39
                        .getEntropyFromMnemonic(AlgoKitBip39.generate24WordMnemonic())
                        .toByteArray(),
                ),
            )

        override fun getMnemonic(): Bip39Mnemonic = Bip39Mnemonic(emptyList())

        override fun generateAddress(index: HdKeyAddressIndex): HdKeyAddress =
            HdKeyAddress(
                address = generateRandomAddress(),
                index = HdKeyAddressIndex(0),
                privateKey = ByteArray(32),
                publicKey = ByteArray(32),
                derivationType = HdKeyAddressDerivationType.Peikert,
            )

        override fun generateAddressLite(index: HdKeyAddressIndex): HdKeyAddressLite =
            HdKeyAddressLite(
                address = generateRandomAddress(),
                index = HdKeyAddressIndex(0),
            )

        override fun generateFalcon24Address(mnemonic2: String): Falcon24 {
            val mnemonic = WalletSdkConstants.SAMPLE_HD_MNEMONIC
            return Falcon24(
                address = spmAlgoApiBridge().getFalconAddressFromMnemonicWithPassphrase(mnemonic),
                publicKey = spmAlgoApiBridge().getFalconPublicKeyFromMnemonicWithPassphrase(mnemonic).toByteArray(),
                privateKey = spmAlgoApiBridge().getFalconPrivateKeyFromMnemonicWithPassphrase(mnemonic).toByteArray(),
            )
        }

        override fun invalidate() {}
    }

private fun generateRandomAddress(): String {
    val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567" // Base32 characters
    val addressLength = 58

    return (1..addressLength)
        .map { alphabet[Random.nextInt(alphabet.length)] }
        .joinToString("")
}
