/*
 * Copyright 2022-2025 Pera Wallet, LDA
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

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
