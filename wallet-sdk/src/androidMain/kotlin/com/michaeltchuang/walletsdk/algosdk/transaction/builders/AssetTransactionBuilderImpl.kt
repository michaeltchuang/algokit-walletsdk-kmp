 

package com.michaeltchuang.walletsdk.algosdk.transaction.builders

import com.michaeltchuang.walletsdk.algosdk.transaction.model.Transaction
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoSdk
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.AssetTransactionPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams
import javax.inject.Inject

internal class AssetTransactionBuilderImpl @Inject constructor(
    private val algoSdk: AlgoSdk
) : AssetTransactionBuilder {

    override fun invoke(
        payload: AssetTransactionPayload,
        params: SuggestedTransactionParams
    ): Transaction.AssetTransaction {
        val txnByteArray = createTxnByteArray(payload, params)
        return Transaction.AssetTransaction(payload.senderAddress, txnByteArray)
    }

    private fun createTxnByteArray(payload: AssetTransactionPayload, params: SuggestedTransactionParams): ByteArray {
        return with(payload) {
            algoSdk.createAssetTransferTxn(
                senderAddress = senderAddress,
                receiverAddress = receiverAddress,
                amount = amount,
                assetId = assetId,
                noteInByteArray = noteInByteArray,
                suggestedTransactionParams = params
            )
        }
    }
}
