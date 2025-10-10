package com.michaeltchuang.walletsdk.transaction.domain.usecase

import com.michaeltchuang.walletsdk.network.model.TrackTransactionRequest
import com.michaeltchuang.walletsdk.transaction.data.TransactionsApiServiceImpl
import com.michaeltchuang.walletsdk.transaction.model.SignedTransactionDetail
import com.michaeltchuang.walletsdk.utils.DataResource
import kotlinx.coroutines.flow.channelFlow

class SendSignedTransactionUseCase(
    private val transactionsApiServiceImpl: TransactionsApiServiceImpl,
) {
    fun sendSignedTransaction(
        signedTransactionDetail: SignedTransactionDetail
    ) = channelFlow<DataResource<String>> {
        send(DataResource.Loading())
        transactionsApiServiceImpl.sendSignedTransaction(signedTransactionDetail.signedTransactionData)
            .use(
                onSuccess = { sendTransactionResponse ->
                    val txnId = sendTransactionResponse.txnId
                    send(getSendTransactionResult(txnId))
                },
                onFailed = { exception, code ->
                    send(DataResource.Error.Api(exception, code))
                }
            )
    }

    private suspend fun getSendTransactionResult(
        txnId: String?
    ): DataResource<String> {
        txnId?.let { transactionId ->
            transactionsApiServiceImpl.postTrackTransaction(TrackTransactionRequest(transactionId))
        }
        return DataResource.Success(txnId.orEmpty())
    }
}
