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
import com.michaeltchuang.walletsdk.transaction.model.OnlineKeyRegTransactionPayload
import io.ktor.util.decodeBase64Bytes
import io.ktor.utils.io.core.toByteArray
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.base64EncodedStringWithOptions
import platform.Foundation.create
import platform.posix.memcpy
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

const val ROUND_THRESHOLD = 1000L

@OptIn(ExperimentalForeignApi::class)
fun ByteArray.toNSData(): NSData {
    if (this.isEmpty()) {
        return NSData()
    }

    // Create NSData with copied bytes
    return this.usePinned { pinned ->
        NSData.create(
            bytes = pinned.addressOf(0),
            length = this.size.toULong(),
        ) ?: NSData()
    }
}

@OptIn(ExperimentalForeignApi::class)
fun NSData.toByteArray(): ByteArray {
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

actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray? = AlgoKitBip39.getSeedFromEntropy(entropy)

@OptIn(ExperimentalForeignApi::class)
actual fun signHdKeyTransaction(
    transactionByteArray: ByteArray,
    seed: ByteArray,
    account: Int,
    change: Int,
    key: Int,
): ByteArray? = ByteArray(0)

@OptIn(ExperimentalForeignApi::class)
actual fun signFalcon24Transaction(
    transactionByteArray: ByteArray,
    publicKey: ByteArray,
    privateKey: ByteArray,
): ByteArray? =
    try {
        val transactionData = transactionByteArray.toNSData()
        val publicKeyBase64 = publicKey.toNSData().base64EncodedStringWithOptions(0u)
        val privateKeyBase64 = privateKey.toNSData().base64EncodedStringWithOptions(0u)

        val signedData =
            spmAlgoApiBridge().signFalconTransactionWithTransactionBytes(
                transactionBytes = transactionData,
                publicKeyBase64 = publicKeyBase64,
                privateKeyBase64 = privateKeyBase64,
            )

        signedData?.toByteArray()
    } catch (e: Exception) {
        println("ERROR signing Falcon transaction: ${e.message}")
        null
    }

@OptIn(ExperimentalEncodingApi::class, ExperimentalForeignApi::class)
actual fun signAlgo25Transaction(
    secretKey: ByteArray,
    transactionByteArray: ByteArray,
): ByteArray =
    try {
        val secretKeyBase64 = Base64.encode(secretKey)
        val transactionBase64 = Base64.encode(transactionByteArray)
        val signedDataBase64 =
            spmAlgoApiBridge().signTransactionWithBase64WithSkBase64(
                skBase64 = secretKeyBase64,
                encodedTxBase64 = transactionBase64,
            )
        val result = Base64.decode(signedDataBase64)

        result
    } catch (e: Exception) {
        println("ERROR signing Algo25 transaction: ${e.message}")
        ByteArray(0)
    }

@OptIn(ExperimentalForeignApi::class)
actual fun createTransaction(payload: OfflineKeyRegTransactionPayload): ByteArray {
    val bridge = spmAlgoApiBridge()

    val firstRound = payload.txnParams.lastRound
    val lastRound = payload.txnParams.lastRound + ROUND_THRESHOLD

    val fee =
        payload.flatFee
            ?.toString()
            ?.toLong()
            ?.toULong() ?: payload.txnParams.fee.toULong()
    val flatFeeEnabled = payload.flatFee != null

    val addressString = payload.senderAddress

    val encodedTx =
        bridge.createOfflineKeyRegTransactionWithSenderAddress(
            senderAddress = payload.senderAddress,
            noteBase64 = payload.note,
            fee = fee,
            flatFee = flatFeeEnabled,
            firstRound = firstRound.toULong(),
            lastRound = lastRound.toULong(),
            genesisHashBase64 = payload.txnParams.genesisHash,
            genesisID = payload.txnParams.genesisId,
        )

    if (encodedTx.length == 0UL) {
        println("ERROR: createOfflineKeyRegTransaction returned empty data")
        return ByteArray(0)
    }

    return encodedTx.toByteArray()
}

@OptIn(ExperimentalForeignApi::class)
actual fun createTransaction(payload: OnlineKeyRegTransactionPayload): ByteArray {
    val bridge = spmAlgoApiBridge()

    val firstRound = payload.txnParams.lastRound
    val lastRound = payload.txnParams.lastRound + ROUND_THRESHOLD

    val fee =
        payload.flatFee
            ?.toString()
            ?.toLong()
            ?.toULong() ?: payload.txnParams.fee.toULong()
    val flatFeeEnabled = payload.flatFee != null

    val encodedTx =
        bridge.createOnlineKeyRegTransactionWithSenderAddress(
            senderAddress = payload.senderAddress,
            noteBase64 = payload.note,
            fee = fee,
            flatFee = flatFeeEnabled,
            firstRound = firstRound.toULong(),
            lastRound = lastRound.toULong(),
            genesisHashBase64 = payload.txnParams.genesisHash,
            genesisID = payload.txnParams.genesisId,
            voteKeyBase64 = payload.voteKey,
            selectionKeyBase64 = payload.selectionPublicKey,
            stateProofKeyBase64 = payload.stateProofKey,
            voteFirstRound = payload.voteFirstRound.toULong(),
            voteLastRound = payload.voteLastRound.toULong(),
            voteKeyDilution = payload.voteKeyDilution.toULong(),
        )

    if (encodedTx.length == 0UL) {
        println("ERROR: createOnlineKeyRegTransaction returned empty data")
        return ByteArray(0)
    }

    return encodedTx.toByteArray()
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

        override fun generateFalcon24Address(mnemonic: String): Falcon24 {
            val address = spmAlgoApiBridge().getFalconAddressFromMnemonicWithPassphrase(mnemonic)
            val publicKeyBase64 =
                spmAlgoApiBridge().getFalconPublicKeyFromMnemonicWithPassphrase(mnemonic)
            val privateKeyBase64 =
                spmAlgoApiBridge().getFalconPrivateKeyFromMnemonicWithPassphrase(mnemonic)

            return Falcon24(
                address = address,
                publicKey = publicKeyBase64.fromBase64ToByteArray(),
                privateKey = privateKeyBase64.fromBase64ToByteArray(),
            )
        }

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
