package com.michaeltchuang.walletsdk.transaction.presentation.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.deeplink.model.KeyRegTransactionDetail
import com.michaeltchuang.walletsdk.foundation.EventDelegate
import com.michaeltchuang.walletsdk.foundation.EventViewModel
import com.michaeltchuang.walletsdk.foundation.StateDelegate
import com.michaeltchuang.walletsdk.foundation.StateViewModel
import com.michaeltchuang.walletsdk.transaction.domain.usecase.CreateKeyRegTransaction
import com.michaeltchuang.walletsdk.transaction.domain.usecase.SendSignedTransactionUseCase
import com.michaeltchuang.walletsdk.transaction.model.KeyRegTransaction
import com.michaeltchuang.walletsdk.transaction.model.SignedTransactionDetail
import com.michaeltchuang.walletsdk.transaction.signmanager.ExternalTransactionSignResult
import com.michaeltchuang.walletsdk.transaction.signmanager.KeyRegTransactionSignManager
import com.michaeltchuang.walletsdk.transaction.signmanager.PendingTransactionRequestManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PendingTransactionRequestViewModel(
    private val sendSignedTransactionUseCase: SendSignedTransactionUseCase,
    private val createKeyRegTransaction: CreateKeyRegTransaction,
    private val keyRegTransactionSignManager: KeyRegTransactionSignManager,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<PendingTransactionRequestViewModel.ViewState> by stateDelegate,
    EventViewModel<PendingTransactionRequestViewModel.ViewEvent> by eventDelegate {
    init {
        stateDelegate.setDefaultState(ViewState.Content)
        viewModelScope.launch {
            keyRegTransactionSignManager.keyRegTransactionSignResultFlow.collect {
                when (it) {
                    is ExternalTransactionSignResult.Success<*> -> {
                        sendSignedTransaction(it.signedTransaction)
                    }

                    is ExternalTransactionSignResult.Error -> {
                        transactionFailed(it.getMessage())
                    }

                    is ExternalTransactionSignResult.TransactionCancelled -> {
                        transactionFailed(it.error.getMessage())
                    }

                    else -> {
                        println("confirmTransaction Failed")
                    }
                }
            }
        }
    }

    fun setup(lifecycle: Lifecycle) {
        keyRegTransactionSignManager.setup(lifecycle)
    }

    fun getPendingTransactionRequest(): KeyRegTransactionDetail? {
        return PendingTransactionRequestManger.getPendingTransactionRequest()
    }

    fun confirmTransaction() {
        getPendingTransactionRequest()?.let {
            stateDelegate.updateState {
                ViewState.Loading
            }
            viewModelScope.launch(Dispatchers.IO) {
                createKeyRegTransaction(it).use(
                    onSuccess = { transaction ->
                        signKeyRegTransaction(transaction)
                    },
                    onFailed = { exception, _ ->
                        transactionFailed(exception.message ?: "Unknown error")
                    },
                )
            }
        }?: run {
            transactionFailed("No pending transaction request found")
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
            sendSignedTransactionUseCase
                .sendSignedTransaction(signedTransactionDetail)
                .collectLatest {
                    it.useSuspended(
                        onSuccess = {
                            eventDelegate.sendEvent(ViewEvent.SendSignedTransactionSuccess)
                            PendingTransactionRequestManger.clearPendingTransactionRequest()
                            println("SendSignedTransaction onSuccess: $it")
                        },
                        onFailed = {
                            println("sendSignedTransaction Failed: ${it.exception?.message}")
                            transactionFailed(it.exception?.message ?: "Unknown error")
                        },
                    )
                }
        }
    }

    private fun transactionFailed(error: String) {
        stateDelegate.updateState { ViewState.Content }
        viewModelScope.launch {
            eventDelegate.sendEvent(ViewEvent.SendSignedTransactionFailed(error))
        }
        println("confirmTransaction Failed: $error")
    }

    sealed interface ViewState {
        data object Loading :
            ViewState

        data object Content :
            ViewState
    }

    sealed interface ViewEvent {
        object SendSignedTransactionSuccess :
            ViewEvent

        data class SendSignedTransactionFailed(
            val error: String,
        ) : ViewEvent
    }
}
