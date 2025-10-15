package com.michaeltchuang.walletsdk.core.transaction.domain.usecase

import com.michaeltchuang.walletsdk.core.network.model.TrackTransactionRequest
import com.michaeltchuang.walletsdk.core.transaction.data.TransactionsApiServiceImpl
import com.michaeltchuang.walletsdk.core.transaction.model.SignedTransactionDetail
import com.michaeltchuang.walletsdk.utils.DataResource
import kotlinx.coroutines.flow.channelFlow

class SendSignedTransactionUseCase(
    private val transactionsApiServiceImpl: TransactionsApiServiceImpl,
) {
    fun sendSignedTransaction(signedTransactionDetail: SignedTransactionDetail) =
        channelFlow<DataResource<String>> {
            send(DataResource.Loading())
            transactionsApiServiceImpl
                .sendSignedTransaction(signedTransactionDetail.signedTransactionData)
                .use(
                    onSuccess = { sendTransactionResponse ->
                        val txnId = sendTransactionResponse.txnId
                        send(getSendTransactionResult(txnId))
                    },
                    onFailed = { exception, code ->
                        send(DataResource.Error.Api(exception, code))
                    },
                )
        }

    private suspend fun getSendTransactionResult(txnId: String?): DataResource<String> {
        txnId?.let { transactionId ->
            transactionsApiServiceImpl.postTrackTransaction(TrackTransactionRequest(transactionId))
        }
        return DataResource.Success(txnId.orEmpty())
    }
}
