package com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk

import com.algorand.algosdk.sdk.Sdk
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoSdkNumberExtensions.toUint64
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.mapper.SuggestedParamsMapper
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.model.SuggestedTransactionParams
import java.math.BigInteger

internal class AlgoSdkImpl(
    private val suggestedParamsMapper: SuggestedParamsMapper,
) : AlgoSdk {
    override fun createAssetTransferTxn(
        senderAddress: String,
        receiverAddress: String,
        amount: BigInteger,
        assetId: Long,
        noteInByteArray: ByteArray?,
        suggestedTransactionParams: SuggestedTransactionParams,
    ): ByteArray =
        Sdk.makeAssetTransferTxn(
            senderAddress,
            receiverAddress,
            "",
            amount.toUint64(),
            noteInByteArray,
            suggestedParamsMapper(suggestedTransactionParams, addGenesis = false),
            assetId,
        )

    override fun createAlgoTransferTxn(
        senderAddress: String,
        receiverAddress: String,
        amount: BigInteger,
        isMax: Boolean,
        noteInByteArray: ByteArray?,
        suggestedTransactionParams: SuggestedTransactionParams,
    ): ByteArray =
        Sdk.makePaymentTxn(
            senderAddress,
            receiverAddress,
            amount.toUint64(),
            noteInByteArray,
            if (isMax) receiverAddress else "",
            suggestedParamsMapper(suggestedTransactionParams, addGenesis = true),
        )

    override fun createRekeyTxn(
        rekeyAddress: String,
        rekeyAdminAddress: String,
        suggestedTransactionParams: SuggestedTransactionParams,
    ): ByteArray =
        Sdk.makeRekeyTxn(
            rekeyAddress,
            rekeyAdminAddress,
            suggestedParamsMapper(suggestedTransactionParams, addGenesis = true),
        )

    override fun createAddAssetTxn(
        address: String,
        assetId: Long,
        suggestedTransactionParams: SuggestedTransactionParams,
    ): ByteArray =
        Sdk.makeAssetAcceptanceTxn(
            address,
            null,
            suggestedParamsMapper(suggestedTransactionParams, addGenesis = true),
            assetId,
        )

    override fun createRemoveAssetTxn(
        senderAddress: String,
        creatorPublicKey: String,
        assetId: Long,
        suggestedTransactionParams: SuggestedTransactionParams,
    ): ByteArray =
        Sdk.makeAssetTransferTxn(
            senderAddress,
            creatorPublicKey,
            creatorPublicKey,
            0L.toUint64(),
            null,
            suggestedParamsMapper(suggestedTransactionParams, addGenesis = false),
            assetId,
        )

    override fun createSendAndRemoveAssetTxn(
        senderAddress: String,
        receiverAddress: String,
        assetId: Long,
        amount: BigInteger,
        noteInByteArray: ByteArray?,
        suggestedTransactionParams: SuggestedTransactionParams,
    ): ByteArray =
        Sdk.makeAssetTransferTxn(
            senderAddress,
            receiverAddress,
            receiverAddress,
            amount.toUint64(),
            noteInByteArray,
            suggestedParamsMapper(suggestedTransactionParams, addGenesis = false),
            assetId,
        )

    override fun transactionMsgpackToJson(txnByteArray: ByteArray): String = Sdk.transactionMsgpackToJson(txnByteArray)
}
