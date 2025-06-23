

package com.michaeltchuang.walletsdk.algosdk.transaction.model

import java.math.BigInteger

data class RawTransaction(
    val amount: String?,
    val fee: Long?,
    val firstValidRound: Long?,
    val genesisId: String?,
    val genesisHash: String?,
    val lastValidRound: Long?,
    val note: String?,
    val receiverAddress: AlgorandAddress?,
    val senderAddress: AlgorandAddress?,
    val transactionType: RawTransactionType,
    val closeToAddress: AlgorandAddress?,
    val rekeyAddress: AlgorandAddress?,
    val assetCloseToAddress: AlgorandAddress?,
    val assetReceiverAddress: AlgorandAddress?,
    val assetAmount: BigInteger?,
    val assetId: Long?,
    val appArgs: List<String>?,
    val appOnComplete: Int?,
    val appId: Long?,
    val appGlobalSchema: ApplicationCallStateSchema?,
    val appLocalSchema: ApplicationCallStateSchema?,
    val appExtraPages: Int?,
    val approvalHash: String?,
    val stateHash: String?,
    val assetIdBeingConfigured: Long?,
    val assetConfigParameters: AssetConfigParameters?,
    val groupId: String?,
)
