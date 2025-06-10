 

package com.michaeltchuang.walletsdk.algosdk.transaction.usecase

import com.michaeltchuang.walletsdk.algosdk.transaction.mapper.RawTransactionMapper
import com.michaeltchuang.walletsdk.algosdk.transaction.model.RawTransaction
import com.michaeltchuang.walletsdk.algosdk.transaction.model.payload.RawTransactionPayload
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoSdk
import com.michaeltchuang.walletsdk.algosdk.foundation.json.JsonSerializer

internal class ParseTransactionMessagePackUseCase(
    private val jsonSerializer: JsonSerializer,
    private val rawTransactionMapper: RawTransactionMapper,
    private val algoSdk: AlgoSdk
) : ParseTransactionMessagePack {

    override fun invoke(txnByteArray: ByteArray): RawTransaction? {
        return try {
            tryParsing(txnByteArray)
        } catch (exception: Exception) {
            null
        }
    }

    private fun tryParsing(txnByteArray: ByteArray): RawTransaction? {
        val transactionJson = algoSdk.transactionMsgpackToJson(txnByteArray)
        val rawTransactionPayload = jsonSerializer.fromJson(transactionJson, RawTransactionPayload::class.java)
            ?: return null
        return rawTransactionMapper(rawTransactionPayload)
    }
}
