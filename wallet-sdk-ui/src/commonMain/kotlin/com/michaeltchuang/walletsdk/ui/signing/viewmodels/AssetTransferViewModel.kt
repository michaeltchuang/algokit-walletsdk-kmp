package com.michaeltchuang.walletsdk.ui.signing.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetTransactionSigner
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import com.michaeltchuang.walletsdk.core.transaction.domain.usecase.SendSignedTransactionUseCase
import com.michaeltchuang.walletsdk.core.transaction.model.SignedTransactionDetail
import com.michaeltchuang.walletsdk.core.transaction.model.TargetUser
import com.michaeltchuang.walletsdk.core.transaction.model.TransactionManagerResult
import com.michaeltchuang.walletsdk.core.transaction.model.TransactionSignData
import com.michaeltchuang.walletsdk.core.transaction.signmanager.PendingTransactionRequestManger
import com.michaeltchuang.walletsdk.core.transaction.signmanager.TransactionSignManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AssetTransferViewModel(
    private val transactionSignManager: TransactionSignManager,
    private val sendSignedTransactionUseCase: SendSignedTransactionUseCase,
    private val getTransactionSigner: GetTransactionSigner,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(), StateViewModel<AssetTransferViewModel.ViewState> by stateDelegate,
    EventViewModel<AssetTransferViewModel.ViewEvent> by eventDelegate {

    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        viewModelScope.launch {
            transactionSignManager.transactionManagerResultStateFlow.collect {
                when (it) {
                    is TransactionManagerResult.Error.GlobalWarningError.Api -> {}
                    is TransactionManagerResult.Error.GlobalWarningError.Defined -> {}
                    is TransactionManagerResult.Error.GlobalWarningError.MinBalanceError -> {}
                    is TransactionManagerResult.LedgerOperationCanceled -> {}
                    is TransactionManagerResult.LedgerScanFailed -> {}
                    is TransactionManagerResult.LedgerWaitingForApproval -> {}
                    is TransactionManagerResult.Loading -> {}
                    is TransactionManagerResult.Success -> {
                        println(it.signedTransactionDetail.toString())
                        sendSignedTransaction(it.signedTransactionDetail)
                    }

                    null -> {}
                }
            }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState
        data object Content : ViewState
    }

    sealed interface ViewEvent {}

    suspend fun createSendTransactionData(): TransactionSignData.Send {
        val senderAddress = "HFKDK46DNWSXYF52TY2IRE4VXYAQSK4IGRBL6Z62RSI5NFL3VJ25KP2JKY"
        val receiverAddress = "EELGCQ2C35XEKWOH2VD63W2LYL4UYBMNVVD7LWQLEMI57DNBRDJLFW3DD4"
        val minBalanceCalculatedAmount = 1000000.toBigInteger()
        val senderAlgoAmount = 8997000.toBigInteger()
        val minimumBalance = 100000L
        val assetId = -7L
        return TransactionSignData.Send(
            senderAccountAddress = senderAddress,
            senderAuthAddress = null,
            senderAccountName = "",
            senderAlgoAmount = senderAlgoAmount,
            minimumBalance = minimumBalance,
            amount = minBalanceCalculatedAmount,
            assetId = assetId,
            note = null,
            targetUser = TargetUser(
                publicKey = receiverAddress,
            ),
            signer = getTransactionSigner(senderAddress),
            isArc59Transaction = false
        )
    }


    fun setup(lifecycle: Lifecycle) {
        transactionSignManager.setup(lifecycle)
    }
    fun sendTransaction() {
        viewModelScope.launch {
            transactionSignManager.initSigningTransactions(
                isGroupTransaction = false,
                createSendTransactionData()
            )
        }
    }

    private fun sendSignedTransaction(signedTransactionDetail: SignedTransactionDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            sendSignedTransactionUseCase
                .sendSignedTransaction(signedTransactionDetail = signedTransactionDetail )
                .collectLatest {
                    it.useSuspended(
                        onSuccess = {
                           // eventDelegate.sendEvent(ConfirmTransactionRequestViewModel.ViewEvent.SendSignedTransactionSuccess(it))

                          //  keyRegTransactionSignManager.manualStopAllResources()
                            println("SendSignedTransaction onSuccess: $it")
                        },
                        onFailed = {
                            println("sendSignedTransaction Failed: ${it.exception?.message}")
                          //  transactionFailed(it.exception?.message ?: "Unknown error")
                        },
                    )
                }
        }
    }

}