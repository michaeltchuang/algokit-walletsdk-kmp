package com.michaeltchuang.walletsdk.core.algosdk.transaction.builders

import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.Transaction
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoSdk
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.model.AddAssetTransactionPayload
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.model.SuggestedTransactionParams
import javax.inject.Inject

internal class AddAssetTransactionBuilderBuilderImpl
    @Inject
    constructor(
        private val algoSdk: AlgoSdk,
    ) : AddAssetTransactionBuilder {
        override fun invoke(
            payload: AddAssetTransactionPayload,
            params: SuggestedTransactionParams,
        ): Transaction.AddAssetTransaction {
            val txnByteArray = createTxnByteArray(payload, params)
            return Transaction.AddAssetTransaction(payload.address, txnByteArray)
        }

        private fun createTxnByteArray(
            payload: AddAssetTransactionPayload,
            params: SuggestedTransactionParams,
        ): ByteArray =
            algoSdk.createAddAssetTxn(
                address = payload.address,
                assetId = payload.assetId,
                suggestedTransactionParams = params,
            )
    }
