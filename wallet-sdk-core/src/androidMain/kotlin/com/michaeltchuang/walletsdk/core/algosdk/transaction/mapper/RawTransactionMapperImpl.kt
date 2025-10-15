package com.michaeltchuang.walletsdk.core.algosdk.transaction.mapper

import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.RawTransaction
import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.payload.RawTransactionPayload
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoSdkAddress

internal class RawTransactionMapperImpl(
    private val algoSdkAddress: AlgoSdkAddress,
    private val rawTransactionTypeMapper: RawTransactionTypeMapper,
    private val assetConfigParametersMapper: AssetConfigParametersMapper,
    private val applicationCallStateSchemaMapper: ApplicationCallStateSchemaMapper,
) : RawTransactionMapper {
    override fun invoke(payload: RawTransactionPayload): RawTransaction =
        RawTransaction(
            amount = payload.amount,
            fee = payload.fee,
            firstValidRound = payload.firstValidRound,
            genesisId = payload.genesisId,
            genesisHash = payload.genesisHash,
            lastValidRound = payload.lastValidRound,
            note = payload.note,
            receiverAddress = payload.receiverAddress?.let { algoSdkAddress.generateAddressFromPublicKey(it) },
            senderAddress = payload.senderAddress?.let { algoSdkAddress.generateAddressFromPublicKey(it) },
            transactionType = rawTransactionTypeMapper(payload.transactionType),
            closeToAddress = payload.closeToAddress?.let { algoSdkAddress.generateAddressFromPublicKey(it) },
            rekeyAddress = payload.rekeyAddress?.let { algoSdkAddress.generateAddressFromPublicKey(it) },
            assetCloseToAddress = payload.assetCloseToAddress?.let { algoSdkAddress.generateAddressFromPublicKey(it) },
            assetReceiverAddress = payload.assetReceiverAddress?.let { algoSdkAddress.generateAddressFromPublicKey(it) },
            assetAmount = payload.assetAmount,
            assetId = payload.assetId,
            appArgs = payload.appArgs,
            appOnComplete = payload.appOnComplete,
            appId = payload.appId,
            appGlobalSchema = applicationCallStateSchemaMapper(payload.appGlobalSchema),
            appLocalSchema = applicationCallStateSchemaMapper(payload.appLocalSchema),
            appExtraPages = payload.appExtraPages,
            approvalHash = payload.approvalHash,
            stateHash = payload.stateHash,
            assetIdBeingConfigured = payload.assetIdBeingConfigured,
            assetConfigParameters = assetConfigParametersMapper(payload.decodedAssetConfigParameters),
            groupId = payload.groupId,
        )
}
