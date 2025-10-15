package com.michaeltchuang.walletsdk.core.algosdk.transaction.builders

import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.Transaction
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.AlgoSdk
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.model.AlgoTransactionPayload
import com.michaeltchuang.walletsdk.core.algosdk.transaction.sdk.model.SuggestedTransactionParams
import javax.inject.Inject

internal class AlgoTransactionBuilderImpl
    @Inject
    constructor(
        private val algoSdk: AlgoSdk,
    ) : AlgoTransactionBuilder {
        override fun invoke(
            payload: AlgoTransactionPayload,
            params: SuggestedTransactionParams,
        ): Transaction.AlgoTransaction {
            val txnByteArray = createTxnByteArray(payload, params)
            return Transaction.AlgoTransaction(payload.senderAddress, txnByteArray)
        }

        private fun createTxnByteArray(
            payload: AlgoTransactionPayload,
            params: SuggestedTransactionParams,
        ): ByteArray =
            with(payload) {
                algoSdk.createAlgoTransferTxn(
                    senderAddress = senderAddress,
                    receiverAddress = receiverAddress,
                    amount = amount,
                    isMax = isMaxAmount,
                    noteInByteArray = noteInByteArray,
                    suggestedTransactionParams = params,
                )
            }
    }
