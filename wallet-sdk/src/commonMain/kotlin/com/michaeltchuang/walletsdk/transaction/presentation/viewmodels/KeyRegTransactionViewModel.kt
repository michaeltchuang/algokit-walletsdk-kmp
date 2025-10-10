package com.michaeltchuang.walletsdk.transaction.presentation.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.deeplink.model.KeyRegTransactionDetail
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import com.michaeltchuang.walletsdk.transaction.domain.usecase.CreateKeyRegTransaction
import com.michaeltchuang.walletsdk.transaction.domain.usecase.SendSignedTransactionUseCase
import com.michaeltchuang.walletsdk.transaction.model.KeyRegTransaction
import com.michaeltchuang.walletsdk.transaction.model.SignedTransactionDetail
import com.michaeltchuang.walletsdk.transaction.signmanager.ExternalTransactionSignResult
import com.michaeltchuang.walletsdk.transaction.signmanager.KeyRegTransactionSignManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class KeyRegTransactionViewModel(
    private val sendSignedTransactionUseCase: SendSignedTransactionUseCase,
    private val createKeyRegTransaction: CreateKeyRegTransaction,
    private val keyRegTransactionSignManager: KeyRegTransactionSignManager,
    private val eventDelegate: EventDelegate<ViewEvent>
) : ViewModel(), EventViewModel<KeyRegTransactionViewModel.ViewEvent> by eventDelegate {

    init {
        viewModelScope.launch {
            keyRegTransactionSignManager.keyRegTransactionSignResultFlow.collect {
                when (it) {
                    is ExternalTransactionSignResult.Success<*> -> {
                        sendSignedTransaction(it.signedTransaction)
                    }

                    is ExternalTransactionSignResult.Loading -> {}
                    else -> {
                        eventDelegate.sendEvent(ViewEvent.SendSignedTransactionFailed("Something went wrong"))
                    }
                }
            }
        }
    }

    fun setup(lifecycle: Lifecycle) {
        keyRegTransactionSignManager.setup(lifecycle)
    }

    fun confirmTransaction(keyRegTransactionDetail: KeyRegTransactionDetail) {
        viewModelScope.launch {
            createKeyRegTransaction(keyRegTransactionDetail).use(
                onSuccess = { transaction ->
                    signKeyRegTransaction(transaction)
                },
                onFailed = { exception, _ ->
                    println("confirmTransaction Failed: ${exception.message.toString()}")
                }
            )
        }
    }

    fun signKeyRegTransaction(keyRegTransaction: KeyRegTransaction) {
        keyRegTransactionSignManager.signKeyRegTransaction(keyRegTransaction)
    }

    fun sendSignedTransaction(signedTransactions: List<Any?>) {
        viewModelScope.launch(Dispatchers.IO) {
            val signedTxnByteArray = signedTransactions.first() as? ByteArray ?: return@launch
            val signedTransactionDetail =
                SignedTransactionDetail.ExternalTransaction(signedTxnByteArray)
            sendSignedTransactionUseCase.sendSignedTransaction(signedTransactionDetail)
                .collectLatest {
                    it.useSuspended(
                        onSuccess = {
                            eventDelegate.sendEvent(ViewEvent.SendSignedTransactionSuccess)
                            println("sendSignedTransaction onSuccess: ${it}")
                        },
                        onFailed = {
                            eventDelegate.sendEvent(
                                ViewEvent.SendSignedTransactionFailed(
                                    it.exception?.message ?: ""
                                )
                            )
                            println("sendSignedTransaction Failed: ${it.exception?.message}")
                        }
                    )
                }
        }
    }

    sealed interface ViewEvent {
        object SendSignedTransactionSuccess : ViewEvent
        data class SendSignedTransactionFailed(val error: String) : ViewEvent
    }

}

