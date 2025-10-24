package com.michaeltchuang.walletsdk.core.foundation.utils

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.michaeltchuang.walletsdk.core.algosdk.makeAssetTransferTxn
import com.michaeltchuang.walletsdk.core.algosdk.makePaymentTxn
import com.michaeltchuang.walletsdk.core.deeplink.utils.AssetConstants.ALGO_ID
import com.michaeltchuang.walletsdk.core.network.model.TransactionParams
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

actual fun ByteArray.signTransaction(secretKey: ByteArray): ByteArray = ByteArray(0)

actual class SuggestedParams

actual class TransactionParams

actual class BytesArray

actual fun TransactionParams.toSuggestedParams(addGenesisId: Boolean): SuggestedParams {
    TODO("Not yet implemented")
}

actual fun ByteArray.signTx(secretKey: ByteArray): ByteArray = ByteArray(0)

actual fun TransactionParams.makeAlgoTx(
    senderAddress: String,
    receiverAddress: String,
    amount: BigInteger,
    isMax: Boolean,
    noteInByteArray: ByteArray?,
): ByteArray =
    makePaymentTxn(
        senderAddress = senderAddress,
        receiverAddress = receiverAddress,
        amount = amount.toString(),
        isMax = isMax,
        noteInByteArray = noteInByteArray,
        toSuggestedParams(),
    )

actual fun TransactionParams.makeAssetTx(
    senderAddress: String,
    receiverAddress: String,
    amount: BigInteger,
    assetId: Long,
    noteInByteArray: ByteArray?,
): ByteArray =
    makeAssetTransferTxn(
        senderAddress = senderAddress,
        receiverAddress = receiverAddress,
        amount = amount.toString(),
        assetId = assetId,
        noteInByteArray = noteInByteArray,
        toSuggestedParams(addGenesisId = false),
    )

actual fun TransactionParams.makeTx(
    senderAddress: String,
    receiverAddress: String,
    amount: BigInteger,
    assetId: Long,
    isMax: Boolean,
    note: String?,
): ByteArray {
    val noteInByteArray = note?.toByteArray(charset = Charsets.UTF_8)

    return if (assetId == ALGO_ID) {
        makeAlgoTx(senderAddress, receiverAddress, amount, isMax, noteInByteArray)
    } else {
        makeAssetTx(senderAddress, receiverAddress, amount, assetId, noteInByteArray)
    }
}

actual fun TransactionParams.makeAddAssetTx(
    publicKey: String,
    assetId: Long,
): ByteArray = ByteArray(0)

actual fun TransactionParams.makeRemoveAssetTx(
    senderAddress: String,
    creatorPublicKey: String,
    assetId: Long,
): ByteArray = ByteArray(0)

actual fun TransactionParams.makeSendAndRemoveAssetTx(
    senderAddress: String,
    receiverAddress: String,
    assetId: Long,
    amount: BigInteger,
    noteInByteArray: ByteArray?,
): ByteArray = ByteArray(0)

actual fun TransactionParams.makeRekeyTx(
    rekeyAddress: String,
    rekeyAdminAddress: String,
): ByteArray = ByteArray(0)
