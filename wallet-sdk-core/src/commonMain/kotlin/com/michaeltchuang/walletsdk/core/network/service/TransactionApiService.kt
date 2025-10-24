package com.michaeltchuang.walletsdk.core.network.service

import com.michaeltchuang.walletsdk.core.foundation.utils.Result
import com.michaeltchuang.walletsdk.core.network.model.SendTransactionResponse
import com.michaeltchuang.walletsdk.core.network.model.TrackTransactionRequest
import com.michaeltchuang.walletsdk.core.network.model.TransactionParams

interface TransactionApiService {
    suspend fun getTransactionParams(): Result<TransactionParams>

    suspend fun postTrackTransaction(trackTransactionRequest: TrackTransactionRequest): Result<Unit>

    suspend fun sendSignedTransaction(rawTransactionData: ByteArray): Result<SendTransactionResponse>
}
