package com.michaeltchuang.walletsdk.transaction.domain.usecase

import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.michaeltchuang.walletsdk.deeplink.model.KeyRegTransactionDetail
import com.michaeltchuang.walletsdk.network.model.TransactionParams
import com.michaeltchuang.walletsdk.network.service.AccountInformationApiService
import com.michaeltchuang.walletsdk.network.service.getAccountRekeyAdminAddress
import com.michaeltchuang.walletsdk.transaction.model.KeyRegTransaction
import com.michaeltchuang.walletsdk.transaction.model.OfflineKeyRegTransactionPayload
import com.michaeltchuang.walletsdk.transaction.model.OnlineKeyRegTransactionPayload
import com.michaeltchuang.walletsdk.utils.Result
import com.michaeltchuang.walletsdk.utils.Result.Error
import com.michaeltchuang.walletsdk.utils.Result.Success

interface CreateKeyRegTransaction {
    suspend operator fun invoke(txnDetail: KeyRegTransactionDetail): Result<KeyRegTransaction>
}

internal class CreateKeyRegTransactionUseCase(
    private val getTransactionParams: GetTransactionParams,
    private val buildKeyRegOfflineTransaction: BuildKeyRegOfflineTransaction,
    private val accountApiService: AccountInformationApiService,
) : CreateKeyRegTransaction {
    override suspend fun invoke(txnDetail: KeyRegTransactionDetail): Result<KeyRegTransaction> =
        when (val params = getTransactionParams()) {
            is Success -> {
                val txnByteArray = createTransactionByteArray(txnDetail, params.data)
                if (txnByteArray == null) {
                    Error(IllegalArgumentException())
                } else {
                    Success(createKeyRegTransactionResult(txnDetail, txnByteArray))
                }
            }

            is Error -> {
                Error(params.exception, params.code)
            }
        }

    private fun createTransactionByteArray(
        txnDetail: KeyRegTransactionDetail,
        params: TransactionParams,
    ): ByteArray? {
        /*   return if (txnDetail.isOnlineKeyRegTxn()) {
               buildKeyRegOnlineTransaction(
                   params = txnDetail.toOnlineTxnPayload(txnDetail, params)
               )
           } else {
               buildKeyRegOfflineTransaction(
                   OfflineKeyRegTransactionPayload(
                       txnDetail.address,
                       txnDetail.fee?.toBigInteger(),
                       txnDetail.note,
                       params
                   )
               )
           }*/
        return buildKeyRegOfflineTransaction(
            OfflineKeyRegTransactionPayload(
                txnDetail.address,
                txnDetail.fee?.toBigInteger(),
                txnDetail.note,
                params,
            ),
        )
    }

    private suspend fun createKeyRegTransactionResult(
        txnDetail: KeyRegTransactionDetail,
        txnByteArray: ByteArray,
    ): KeyRegTransaction =
        KeyRegTransaction(
            transactionByteArray = txnByteArray,
            accountAddress = txnDetail.address,
            accountAuthAddress = accountApiService.getAccountRekeyAdminAddress(txnDetail.address),
            isRekeyedToAnotherAccount = false,
        )

    private fun KeyRegTransactionDetail.toOnlineTxnPayload(
        txnDetail: KeyRegTransactionDetail,
        params: TransactionParams,
    ): OnlineKeyRegTransactionPayload =
        OnlineKeyRegTransactionPayload(
            senderAddress = address,
            selectionPublicKey = selectionPublicKey.orEmpty(),
            stateProofKey = sprfkey.orEmpty(),
            voteKey = voteKey.orEmpty(),
            voteFirstRound = voteFirstRound.orEmpty(),
            voteLastRound = voteLastRound.orEmpty(),
            voteKeyDilution = voteKeyDilution.orEmpty(),
            txnParams = params,
            note = xnote ?: note,
            flatFee = txnDetail.fee?.toBigInteger(),
        )

    private fun KeyRegTransactionDetail.isOnlineKeyRegTxn(): Boolean =
        !voteKey.isNullOrBlank() &&
            !selectionPublicKey.isNullOrBlank() &&
            !voteFirstRound.isNullOrBlank() &&
            !voteLastRound.isNullOrBlank() &&
            !voteKeyDilution.isNullOrBlank()
}
