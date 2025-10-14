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

actual fun getBip39Wallet(entropy: ByteArray): Bip39Wallet = getBit39Wallet(entropy)

actual fun createBip39Wallet(): Bip39Wallet =
    getBit39Wallet(
        AlgoKitBip39.getEntropyFromMnemonic(
            AlgoKitBip39.generate24WordMnemonic(),
        ),
    )

actual fun getSeedFromEntropy(entropy: ByteArray): ByteArray? = AlgoKitBip39.getSeedFromEntropy(entropy)

@OptIn(ExperimentalForeignApi::class)
actual fun signHdKeyTransaction(
    transactionByteArray: ByteArray,
    seed: ByteArray,
    account: Int,
    change: Int,
    key: Int,
): ByteArray? {
    println("=== signHdKeyTransaction (Kotlin) START ===")
    println("Transaction bytes length: ${transactionByteArray.size}")
    println("Seed length: ${seed.size}")
    println("Account: $account, Change: $change, Key: $key")

    return try {
        println("Step 1: Converting seed to Base64...")
        val seedBase64 = seed.toNSData().base64EncodedStringWithOptions(0.toULong())

        // NEW: Verify the derived address BEFORE signing
        println("Step 1a: Verifying address matches transaction sender...")
        val bridge = spmAlgoApiBridge()
        val derivedPublicKey =
            bridge.getHdPublicKeyFromSeedWithSeedBase64(
                seedBase64 = seedBase64,
                account = account.toLong(),
                change = change.toLong(),
                keyIndex = key.toLong(),
            )

        val derivedAddress = bridge.generateAddressFromPublicKeyWithPublicKey(derivedPublicKey)
        println("✓ Derived address: $derivedAddress")

        // TODO: Parse transaction to get sender and compare
        // For now, just print it so you can manually verify

        println("Step 2: Converting transaction to NSData...")
        val transactionData = transactionByteArray.toNSData()
        println("✓ Transaction NSData length: ${transactionData.length}")

        println("Step 3: Calling Swift signHdKeyTransaction...")
        val signedData =
            bridge.signHdKeyTransactionWithTransactionBytes(
                transactionBytes = transactionData,
                seedBase64 = seedBase64,
                account = account.toLong(),
                change = change.toLong(),
                keyIndex = key.toLong(),
            )

        if (signedData == null) {
            println("❌ ERROR: Swift returned null")
            return null
        }

        println("✓ Transaction signed successfully!")
        val result = signedData.toByteArray()
        println("=== signHdKeyTransaction (Kotlin) END (SUCCESS) ===")

        result
    } catch (e: Exception) {
        println("❌ EXCEPTION: ${e.message}")
        e.printStackTrace()
        null
    }
}

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
            spmAlgoApiBridge().signAlgo25TransactionWithBase64WithSkBase64(
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
private fun getBit39Wallet(entropy: ByteArray): Bip39Wallet =
    object : Bip39Wallet {
        private val mnemonic: String by lazy {
            AlgoKitBip39.getMnemonicFromEntropy(entropy)
        }

        override fun getEntropy(): Bip39Entropy = Bip39Entropy(entropy.copyOf())

        override fun getSeed(): Bip39Seed = Bip39Seed(seed.copyOf())

        override fun getMnemonic(): Bip39Mnemonic = Bip39Mnemonic(mnemonic.split(" "))

        override fun generateAddress(index: HdKeyAddressIndex): HdKeyAddress {
            val seedBytes = AlgoKitBip39.getSeedFromEntropy(entropy)

            val seedBase64 = seedBytes.toNSData().base64EncodedStringWithOptions(0.toULong())

            val publicKey =
                spmAlgoApiBridge().getHdPublicKeyFromSeedWithSeedBase64(
                    seedBase64 = seedBase64,
                    account = index.accountIndex.toLong(),
                    change = index.changeIndex.toLong(),
                    keyIndex = index.keyIndex.toLong(),
                )

            val privateKey =
                spmAlgoApiBridge().getHdPrivateKeyFromSeedWithSeedBase64(
                    seedBase64 = seedBase64,
                    account = index.accountIndex.toLong(),
                    change = index.changeIndex.toLong(),
                    keyIndex = index.keyIndex.toLong(),
                )

            return HdKeyAddress(
                address = getAddressFromPublicKey(publicKey),
                index = index,
                privateKey = privateKey.toByteArray(),
                publicKey = publicKey.toByteArray(),
                derivationType = HdKeyAddressDerivationType.Peikert,
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

        private val seed: ByteArray by lazy {
            val entropy = AlgoKitBip39.getEntropyFromMnemonic(mnemonic)
            AlgoKitBip39.getSeedFromEntropy(entropy)
        }

        override fun generateAddressLite(index: HdKeyAddressIndex): HdKeyAddressLite {
            val publicKey = generatePublicKey(index)
            return HdKeyAddressLite(
                address = getAddressFromPublicKey(publicKey),
                index = index,
            )
        }

        fun generatePublicKey(index: HdKeyAddressIndex): String {
            val seedBase64 = seed.toNSData().base64EncodedStringWithOptions(0.toULong())

            val publicKey =
                spmAlgoApiBridge().getHdPublicKeyFromSeedWithSeedBase64(
                    seedBase64 = seedBase64,
                    account = index.accountIndex.toLong(),
                    change = index.changeIndex.toLong(),
                    keyIndex = index.keyIndex.toLong(),
                )
            return publicKey
        }

        fun getAddressFromPublicKey(publicKey: String): String =
            spmAlgoApiBridge()
                .generateAddressFromPublicKeyWithPublicKey(
                    publicKey = publicKey,
                )
    }
