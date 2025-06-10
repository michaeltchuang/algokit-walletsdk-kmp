 

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk

import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams
import java.math.BigInteger

interface AlgoSdk {

    fun createAssetTransferTxn(
        senderAddress: String,
        receiverAddress: String,
        amount: BigInteger,
        assetId: Long,
        noteInByteArray: ByteArray?,
        suggestedTransactionParams: SuggestedTransactionParams
    ): ByteArray

    fun createAlgoTransferTxn(
        senderAddress: String,
        receiverAddress: String,
        amount: BigInteger,
        isMax: Boolean,
        noteInByteArray: ByteArray?,
        suggestedTransactionParams: SuggestedTransactionParams
    ): ByteArray

    fun createRekeyTxn(
        rekeyAddress: String,
        rekeyAdminAddress: String,
        suggestedTransactionParams: SuggestedTransactionParams
    ): ByteArray

    fun createAddAssetTxn(
        address: String,
        assetId: Long,
        suggestedTransactionParams: SuggestedTransactionParams
    ): ByteArray

    fun createRemoveAssetTxn(
        senderAddress: String,
        creatorPublicKey: String,
        assetId: Long,
        suggestedTransactionParams: SuggestedTransactionParams
    ): ByteArray

    fun createSendAndRemoveAssetTxn(
        senderAddress: String,
        receiverAddress: String,
        assetId: Long,
        amount: BigInteger,
        noteInByteArray: ByteArray?,
        suggestedTransactionParams: SuggestedTransactionParams
    ): ByteArray

    fun transactionMsgpackToJson(txnByteArray: ByteArray): String
}
