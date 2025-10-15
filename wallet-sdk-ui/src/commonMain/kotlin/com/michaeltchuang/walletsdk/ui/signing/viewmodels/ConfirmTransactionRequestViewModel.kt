package com.michaeltchuang.walletsdk.ui.signing.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetLocalAccount
import com.michaeltchuang.walletsdk.core.deeplink.model.KeyRegTransactionDetail
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import com.michaeltchuang.walletsdk.core.transaction.domain.usecase.CreateKeyRegTransaction
import com.michaeltchuang.walletsdk.core.transaction.domain.usecase.SendSignedTransactionUseCase
import com.michaeltchuang.walletsdk.core.transaction.model.KeyRegTransaction
import com.michaeltchuang.walletsdk.core.transaction.model.SignedTransactionDetail
import com.michaeltchuang.walletsdk.core.transaction.signmanager.ExternalTransactionSignResult
import com.michaeltchuang.walletsdk.core.transaction.signmanager.KeyRegTransactionSignManager
import com.michaeltchuang.walletsdk.core.transaction.signmanager.PendingTransactionRequestManger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ConfirmTransactionRequestViewModel(
    private val sendSignedTransactionUseCase: SendSignedTransactionUseCase,
    private val createKeyRegTransaction: CreateKeyRegTransaction,
    private val keyRegTransactionSignManager: KeyRegTransactionSignManager,
    private val getLocalAccount: GetLocalAccount,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<ConfirmTransactionRequestViewModel.ViewState> by stateDelegate,
    EventViewModel<ConfirmTransactionRequestViewModel.ViewEvent> by eventDelegate {
    private val _minimumFee = MutableStateFlow("0.001")
    val minimumFee: StateFlow<String> = _minimumFee.asStateFlow()

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

    fun getPendingTransactionRequest(): KeyRegTransactionDetail? = PendingTransactionRequestManger.getPendingTransactionRequest()

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
        } ?: run {
            transactionFailed("No pending transaction request found")
        }
    }

    fun calculateMinimumFee(txnDetail: KeyRegTransactionDetail?) {
        viewModelScope.launch(Dispatchers.IO) {
            val account = getLocalAccount.invoke(txnDetail?.address ?: return@launch)
            val isOnlineKeyReg =
                !txnDetail.voteKey.isNullOrEmpty() &&
                    !txnDetail.selectionPublicKey.isNullOrEmpty() &&
                    !txnDetail.voteFirstRound.isNullOrEmpty() &&
                    !txnDetail.voteLastRound.isNullOrEmpty() &&
                    !txnDetail.voteKeyDilution.isNullOrEmpty() &&
                    !txnDetail.sprfkey.isNullOrEmpty()

            val fee =
                when {
                    account is LocalAccount.Falcon24 && isOnlineKeyReg -> "2.003"
                    account is LocalAccount.Falcon24 && !isOnlineKeyReg -> "0.004"
                    isOnlineKeyReg -> "2.000"
                    else -> "0.001"
                }
            _minimumFee.value = fee
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
                            eventDelegate.sendEvent(ViewEvent.SendSignedTransactionSuccess(it))
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
        data class SendSignedTransactionSuccess(
            val transactionId: String,
        ) : ViewEvent

        data class SendSignedTransactionFailed(
            val error: String,
        ) : ViewEvent
    }
}
