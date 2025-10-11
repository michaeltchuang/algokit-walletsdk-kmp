
package com.michaeltchuang.walletsdk.transaction.external

import androidx.lifecycle.Lifecycle
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetAlgo25SecretKey
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetHdSeed
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetTransactionSigner
import com.michaeltchuang.walletsdk.algosdk.signHdKeyTransaction
import com.michaeltchuang.walletsdk.foundation.utils.ListQueuingHelper
import com.michaeltchuang.walletsdk.foundation.utils.clearFromMemory
import com.michaeltchuang.walletsdk.foundation.utils.signTransaction
import com.michaeltchuang.walletsdk.network.model.TransactionSigner
import com.michaeltchuang.walletsdk.transaction.model.ExternalTransaction
import com.michaeltchuang.walletsdk.transaction.signmanager.ExternalTransactionQueuingHelper
import com.michaeltchuang.walletsdk.transaction.signmanager.ExternalTransactionSignResult
import com.michaeltchuang.walletsdk.utils.LifecycleScopedCoroutineOwner
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class ExternalTransactionSignManager<TRANSACTION : ExternalTransaction>(
    private val externalTransactionQueuingHelper: ExternalTransactionQueuingHelper,
    private val getTransactionSigner: GetTransactionSigner,
    private val getAlgo25SecretKey: GetAlgo25SecretKey,
    private val getHdSeed: GetHdSeed,
    private val getLocalAccount: GetLocalAccount,
) : LifecycleScopedCoroutineOwner() {
    private val _signResultFlow =
        MutableStateFlow<ExternalTransactionSignResult>(
            ExternalTransactionSignResult.NotInitialized,
        )
    protected val signResultFlow: StateFlow<ExternalTransactionSignResult>
        get() = _signResultFlow

    protected var transaction: List<TRANSACTION>? = null

    private val signHelperListener =
        object : ListQueuingHelper.Listener<ExternalTransaction, ByteArray> {
            override fun onAllItemsDequeued(signedTransactions: List<ByteArray?>) {
                transaction?.run {
                    _signResultFlow.value = ExternalTransactionSignResult.Success(this, signedTransactions)
                }
            }

            override fun onNextItemToBeDequeued(
                transaction: ExternalTransaction,
                currentItemIndex: Int,
                totalItemCount: Int,
            ) {
                transaction.signTransaction(
                    currentTransactionIndex = currentItemIndex,
                    totalTransactionCount = totalItemCount,
                )
            }
        }

    fun setup(lifecycle: Lifecycle) {
        assignToLifecycle(lifecycle)
        externalTransactionQueuingHelper.initListener(signHelperListener)
    }

    open fun signTransaction(transaction: List<TRANSACTION>) {
        postResult(ExternalTransactionSignResult.Loading)
        this.transaction = transaction
        externalTransactionQueuingHelper.initItemsToBeEnqueued(transaction)
    }

    private fun ExternalTransaction.signTransaction(
        currentTransactionIndex: Int?,
        totalTransactionCount: Int?,
    ) {
        currentScope.launch {
            when (val transactionSigner = getTransactionSigner(accountAddress)) {
                is TransactionSigner.SignerNotFound -> {
                    externalTransactionQueuingHelper.cacheDequeuedItem(null)
                }
                is TransactionSigner.Algo25 -> {
                    signTransactionWithSecretKey(this@signTransaction, getAlgo25SecretKey(transactionSigner.address)!!)
                }
                is TransactionSigner.HdKey -> {
                    signHdTransaction(this@signTransaction, transactionSigner.address)
                }
                is TransactionSigner.LedgerBle -> {
                    //       sendTransactionWithLedger(transactionSigner, currentTransactionIndex, totalTransactionCount)
                }
            }
        }
    }

    private fun signTransactionWithSecretKey(
        transaction: ExternalTransaction,
        secretKey: ByteArray,
    ) {
        val signedTransaction = transaction.transactionByteArray?.signTransaction(secretKey)
        onTransactionSigned(transaction, signedTransaction)
    }

    private suspend fun signHdTransaction(
        transaction: ExternalTransaction,
        accountAddress: String,
    ) {
        val transactionBytes = transaction.transactionByteArray ?: return handleSignError(transaction)
        val hdKey = getLocalAccount(accountAddress) as? LocalAccount.HdKey ?: return handleSignError(transaction)
        val seed = getHdSeed(seedId = hdKey.seedId) ?: return handleSignError(transaction)

        val transactionSignedByteArray =
            signHdKeyTransaction(
                transactionBytes,
                seed.copyOf(),
                hdKey.account,
                hdKey.change,
                hdKey.keyIndex,
            ) ?: return handleSignError(transaction)

        seed.clearFromMemory()
        onTransactionSigned(transaction, transactionSignedByteArray)
    }

    private fun handleSignError(transaction: ExternalTransaction) {
        onTransactionSigned(transaction, null)
    }

    protected open fun onTransactionSigned(
        transaction: ExternalTransaction,
        signedTransaction: ByteArray?,
    ) {
        externalTransactionQueuingHelper.cacheDequeuedItem(signedTransaction)
    }

    private fun postResult(transactionSignResult: ExternalTransactionSignResult) {
        _signResultFlow.value = transactionSignResult
    }

    override fun stopAllResources() {
        externalTransactionQueuingHelper.clearCachedData()
        transaction = null
    }

    fun manualStopAllResources() {
        this.stopAllResources()
        currentScope.coroutineContext.cancelChildren()
    }
}
