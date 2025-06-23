

package com.michaeltchuang.walletsdk.algosdk.transaction.builders

import com.michaeltchuang.walletsdk.algosdk.transaction.model.Transaction
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoSdk
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.AddAssetTransactionPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams
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
        ): ByteArray {
            return algoSdk.createAddAssetTxn(
                address = payload.address,
                assetId = payload.assetId,
                suggestedTransactionParams = params,
            )
        }
    }
