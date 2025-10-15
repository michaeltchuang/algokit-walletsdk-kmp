package com.michaeltchuang.walletsdk.core.transaction.signmanager

import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAlgo25SecretKey
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetFalcon24SecretKey
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetHdSeed
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetLocalAccount
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetTransactionSigner
import com.michaeltchuang.walletsdk.core.transaction.external.ExternalTransactionSignManager
import com.michaeltchuang.walletsdk.core.transaction.model.KeyRegTransaction
import kotlinx.coroutines.flow.map

class KeyRegTransactionSignManager(
    externalTransactionQueuingHelper: ExternalTransactionQueuingHelper,
    getTransactionSigner: GetTransactionSigner,
    getAlgo25SecretKey: GetAlgo25SecretKey,
    getFalcon24SecretKey: GetFalcon24SecretKey,
    getHdSeed: GetHdSeed,
    getLocalAccount: GetLocalAccount,
) : ExternalTransactionSignManager<KeyRegTransaction>(
        externalTransactionQueuingHelper,
        getTransactionSigner,
        getAlgo25SecretKey,
        getFalcon24SecretKey,
        getHdSeed,
        getLocalAccount,
    ) {
    private var unsignedTransaction: KeyRegTransaction? = null

    val keyRegTransactionSignResultFlow =
        signResultFlow.map {
            when (it) {
                is ExternalTransactionSignResult.Success<*> ->
                    mapSignedTransaction(
                        unsignedTransaction,
                        it.signedTransactionsByteArray,
                    )

                else -> it
            }
        }

    fun signKeyRegTransaction(keyRegTransaction: KeyRegTransaction) {
        unsignedTransaction = keyRegTransaction
        signTransaction(listOf(keyRegTransaction))
    }

    private fun mapSignedTransaction(
        transaction: KeyRegTransaction?,
        signedTransactions: List<ByteArray?>?,
    ): ExternalTransactionSignResult {
        if (transaction == null) return ExternalTransactionSignResult.Error.Defined()
        val signedTransaction = signedTransactions?.firstOrNull()
        return if (signedTransaction == null) {
            ExternalTransactionSignResult.Error.Defined()
        } else {
            ExternalTransactionSignResult.Success(listOf(signedTransaction))
        }
    }
}
