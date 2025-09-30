

package com.michaeltchuang.walletsdk.algosdk.transaction.builders

import com.michaeltchuang.walletsdk.algosdk.transaction.model.Transaction
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoSdk
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.RemoveAssetTransactionPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams
import javax.inject.Inject

internal class RemoveAssetTransactionBuilderImpl
    @Inject
    constructor(
        private val algoSdk: AlgoSdk,
    ) : RemoveAssetTransactionBuilder {
        override fun invoke(
            payload: RemoveAssetTransactionPayload,
            params: SuggestedTransactionParams,
        ): Transaction.RemoveAssetTransaction {
            val txnByteArray = createTxnByteArray(payload, params)
            return Transaction.RemoveAssetTransaction(payload.senderAddress, txnByteArray)
        }

        private fun createTxnByteArray(
            payload: RemoveAssetTransactionPayload,
            params: SuggestedTransactionParams,
        ): ByteArray =
            with(payload) {
                algoSdk.createRemoveAssetTxn(
                    senderAddress = senderAddress,
                    assetId = assetId,
                    suggestedTransactionParams = params,
                    creatorPublicKey = creatorAddress,
                )
            }
    }
