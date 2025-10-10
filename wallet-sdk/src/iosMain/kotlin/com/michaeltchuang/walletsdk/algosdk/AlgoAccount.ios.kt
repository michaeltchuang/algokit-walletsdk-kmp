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
import com.michaeltchuang.walletsdk.transaction.model.OfflineKeyRegTransactionPayload
import io.ktor.util.decodeBase64Bytes
import io.ktor.utils.io.core.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes
import platform.posix.index
import platform.posix.memcpy

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData =
    this.usePinned { pinned ->
        NSData.dataWithBytes(
            bytes = pinned.addressOf(0),
            length = this.size.toULong(),
        )
    }

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
    val length = this.length.toInt()

    if (length == 0) return ByteArray(0)

    val result = ByteArray(length)

    result.usePinned { pinned ->
        memcpy(pinned.addressOf(0), this.bytes, length.toULong())
    }

    return result
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

actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet {
    val mnemonic = AlgoKitBip39.getMnemonicFromEntropy(entropy)
    return getBit39Wallet(mnemonic)
}

actual fun createBip39Wallet(): Bip39Wallet =
    getBit39Wallet(
        AlgoKitBip39.generate24WordMnemonic(),
    )

actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray? =
    AlgoKitBip39.getSeedFromEntropy(entropy)

actual fun signHdKeyTransaction(
    transactionByteArray: ByteArray,
    seed: ByteArray,
    account: Int,
    change: Int,
    key: Int
): ByteArray? {
    return ByteArray(0)
}

actual fun sdkSignTransaction(secretKey: ByteArray, signTx: ByteArray): ByteArray {
    return ByteArray(0)
}

actual fun createTransaction(payload: OfflineKeyRegTransactionPayload): ByteArray{
    return ByteArray(0)
}

@OptIn(ExperimentalForeignApi::class)
private fun getBit39Wallet(mnemonic: String): Bip39Wallet =
    object : Bip39Wallet {
        override fun getEntropy(): Bip39Entropy =
            Bip39Entropy(
                AlgoKitBip39
                    .getEntropyFromMnemonic(AlgoKitBip39.generate24WordMnemonic()),
            )

        override fun getSeed(): Bip39Seed =
            Bip39Seed(
                AlgoKitBip39.getSeedFromEntropy(
                    AlgoKitBip39
                        .getEntropyFromMnemonic(AlgoKitBip39.generate24WordMnemonic()),
                ),
            )

        override fun getMnemonic(): Bip39Mnemonic = Bip39Mnemonic(mnemonic.split(" "))

        override fun generateAddress(index: HdKeyAddressIndex): HdKeyAddress {
            val publicKey = generatePublicKey(index)
            val privateKey =
                spmAlgoApiBridge().getHdPrivateKeyWithMnemonic(
                    mnemonic,
                    index.accountIndex.toLong(),
                    index.changeIndex.toLong(),
                    index.keyIndex.toLong(),
                )

            return HdKeyAddress(
                address = getAddressFromPublicKey(publicKey),
                index = index,
                privateKey = privateKey.toByteArray(),
                publicKey = publicKey.toByteArray(),
                derivationType = HdKeyAddressDerivationType.Peikert,
            )
        }

        override fun generateAddressLite(index: HdKeyAddressIndex): HdKeyAddressLite {
            val publicKey = generatePublicKey(index)
            return HdKeyAddressLite(
                address = getAddressFromPublicKey(publicKey),
                index = index,
            )
        }

        override fun generateFalcon24Address(mnemonic: String): Falcon24 =
            Falcon24(
                address = spmAlgoApiBridge().getFalconAddressFromMnemonicWithPassphrase(mnemonic),
                publicKey = spmAlgoApiBridge().getFalconPublicKeyFromMnemonicWithPassphrase(mnemonic).toByteArray(),
                privateKey = spmAlgoApiBridge().getFalconPrivateKeyFromMnemonicWithPassphrase(mnemonic).toByteArray(),
            )

        override fun invalidate() {}

        fun generatePublicKey(index: HdKeyAddressIndex): String {
            val publicKey =
                spmAlgoApiBridge().getHdPublicKeyWithMnemonic(
                    mnemonic,
                    index.accountIndex.toLong(),
                    index.changeIndex.toLong(),
                    index.keyIndex.toLong(),
                )
            return publicKey
        }

        fun getAddressFromPublicKey(publicKey: String): String =
            spmAlgoApiBridge()
                .generateAddressFromPublicKeyWithPublicKey(
                    publicKey = publicKey,
                )
    }
