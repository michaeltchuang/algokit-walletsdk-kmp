

package com.michaeltchuang.walletsdk.algosdk.transaction.builders

import com.michaeltchuang.walletsdk.algosdk.transaction.model.Transaction
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoSdk
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.RekeyTransactionPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model.SuggestedTransactionParams
import javax.inject.Inject

internal class RekeyTransactionBuilderImpl
    @Inject
    constructor(
        private val algoSdk: AlgoSdk,
    ) : RekeyTransactionBuilder {
        override fun invoke(
            payload: RekeyTransactionPayload,
            params: SuggestedTransactionParams,
        ): Transaction.RekeyTransaction {
            val txnByteArray = createTxnByteArray(payload, params)
            return Transaction.RekeyTransaction(payload.address, txnByteArray)
        }

        private fun createTxnByteArray(
            payload: RekeyTransactionPayload,
            params: SuggestedTransactionParams,
        ): ByteArray =
            algoSdk.createRekeyTxn(
                rekeyAddress = payload.address,
                rekeyAdminAddress = payload.rekeyAdminAddress,
                suggestedTransactionParams = params,
            )
    }
