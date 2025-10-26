package com.michaeltchuang.walletsdk.ui.signing.viewmodels

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAccountAlgoBalance
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAccountMinimumBalance
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
import com.michaeltchuang.walletsdk.core.transaction.signmanager.TransactionSignManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AssetTransferConfirmViewModel(
    private val transactionSignManager: TransactionSignManager,
    private val sendSignedTransactionUseCase: SendSignedTransactionUseCase,
    private val getTransactionSigner: GetTransactionSigner,
    private val getAccountAlgoBalance: GetAccountAlgoBalance,
    private val getAccountMinimumBalance: GetAccountMinimumBalance,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<AssetTransferConfirmViewModel.ViewState> by stateDelegate,
    EventViewModel<AssetTransferConfirmViewModel.ViewEvent> by eventDelegate {
    private var senderAddress: String = ""
    private var receiverAddress: String = ""
    private var transferAmount: String = ""
    private var transferNote: String = ""

    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        viewModelScope.launch {
            transactionSignManager.transactionManagerResultStateFlow.collect {
                when (it) {
                    is TransactionManagerResult.Error.GlobalWarningError.Api -> {
                        eventDelegate.sendEvent(ViewEvent.ShowError("API error occurred"))
                        restoreContentState()
                    }

                    is TransactionManagerResult.Error.GlobalWarningError.Defined -> {
                        eventDelegate.sendEvent(
                            ViewEvent.ShowError(
                                it.error,
                            ),
                        )
                        restoreContentState()
                    }

                    is TransactionManagerResult.Error.GlobalWarningError.MinBalanceError -> {
                        eventDelegate.sendEvent(ViewEvent.ShowError("Insufficient balance for minimum requirements"))
                        restoreContentState()
                    }

                    is TransactionManagerResult.LedgerOperationCanceled -> {
                        eventDelegate.sendEvent(ViewEvent.ShowError("Ledger operation cancelled"))
                        restoreContentState()
                    }

                    is TransactionManagerResult.LedgerScanFailed -> {
                        eventDelegate.sendEvent(ViewEvent.ShowError("Ledger scan failed"))
                        restoreContentState()
                    }

                    is TransactionManagerResult.LedgerWaitingForApproval -> {}
                    is TransactionManagerResult.Loading -> {
                        stateDelegate.updateState { ViewState.Confirming }
                    }

                    is TransactionManagerResult.Success -> {
                        println(it.signedTransactionDetail.toString())
                        sendSignedTransaction(it.signedTransactionDetail)
                    }

                    null -> {}
                }
            }
        }
    }

    // Setter methods
    fun setSenderAddress(address: String) {
        senderAddress = address
        updateContentState()
        fetchAccountBalance(address)
    }

    fun setReceiverAddress(address: String) {
        receiverAddress = address
        updateContentState()
    }

    fun setAmount(amount: String) {
        transferAmount = amount
        updateContentState()
    }

    fun setNote(note: String) {
        transferNote = note
        updateContentState()
    }

    private fun updateContentState() {
        val currentState = stateDelegate.state.value
        val currentBalance =
            if (currentState is ViewState.Content) {
                currentState.accountBalance
            } else {
                null
            }

        stateDelegate.updateState {
            ViewState.Content(
                senderAddress = senderAddress,
                receiverAddress = receiverAddress,
                amount = transferAmount,
                accountBalance = currentBalance,
                note = transferNote,
            )
        }
    }

    private fun fetchAccountBalance(address: String) {
        viewModelScope.launch {
            try {
                val balance = getAccountAlgoBalance(address)
                val currentState = stateDelegate.state.value
                if (currentState is ViewState.Content) {
                    stateDelegate.updateState {
                        currentState.copy(accountBalance = balance?.toString() ?: "0")
                    }
                }
                println("Fetched account balance: ${balance?.toString() ?: "0"}")
            } catch (e: Exception) {
                println("Exception fetching account balance: ${e.message}")
                val currentState = stateDelegate.state.value
                if (currentState is ViewState.Content) {
                    stateDelegate.updateState {
                        currentState.copy(accountBalance = "0")
                    }
                }
            }
        }
    }

    private fun restoreContentState() {
        stateDelegate.updateState {
            ViewState.Content(
                senderAddress = senderAddress,
                receiverAddress = receiverAddress,
                amount = transferAmount,
                accountBalance = (it as? ViewState.Content)?.accountBalance,
            )
        }
    }

    suspend fun createSendTransactionData(): TransactionSignData.Send? {
        // Validate and convert amount string to microAlgos (1 ALGO = 1,000,000 microAlgos)
        val amountBigInteger =
            try {
                BigInteger.parseString(transferAmount)
            } catch (e: Exception) {
                eventDelegate.sendEvent(ViewEvent.ShowError("Invalid amount format: $transferAmount"))
                return null
            }

        if (amountBigInteger <= BigInteger.ZERO) {
            eventDelegate.sendEvent(ViewEvent.ShowError("Amount must be greater than 0"))
            return null
        }

        // Fetch the sender's actual balance
        val senderAlgoAmount =
            try {
                getAccountAlgoBalance(senderAddress) ?: run {
                    eventDelegate.sendEvent(ViewEvent.ShowError("Unable to fetch sender account balance"))
                    return null
                }
            } catch (e: Exception) {
                eventDelegate.sendEvent(ViewEvent.ShowError("Error fetching balance: ${e.message}"))
                return null
            }

        // Fetch the sender's minimum balance
        val minimumBalance =
            try {
                getAccountMinimumBalance(senderAddress) ?: run {
                    eventDelegate.sendEvent(ViewEvent.ShowError("Unable to fetch minimum balance"))
                    return null
                }
            } catch (e: Exception) {
                eventDelegate.sendEvent(ViewEvent.ShowError("Error fetching minimum balance: ${e.message}"))
                return null
            }

        val amountInMicroAlgos = amountBigInteger

        // Validate that sender has enough balance
        val fee = 1000.toBigInteger() // 0.001 ALGO in microAlgos
        val totalRequired = amountInMicroAlgos + fee + minimumBalance.toBigInteger()
        if (senderAlgoAmount < totalRequired) {
            val availableInMicroAlgos = (senderAlgoAmount - minimumBalance.toBigInteger() - fee)
            val availableInMicroAlgosBigDecimal =
                BigDecimal.parseString(availableInMicroAlgos.toString())
            val availableToSend = availableInMicroAlgosBigDecimal / BigDecimal.fromInt(1000000)
            eventDelegate.sendEvent(
                ViewEvent.ShowError(
                    "Insufficient balance. Available to send: ${availableToSend.toStringExpanded()} ALGO",
                ),
            )
            return null
        }

        val assetId = -7L
        return TransactionSignData.Send(
            senderAccountAddress = senderAddress,
            senderAuthAddress = null,
            senderAccountName = "",
            senderAlgoAmount = senderAlgoAmount,
            minimumBalance = minimumBalance,
            amount = amountInMicroAlgos,
            assetId = assetId,
            note = transferNote,
            targetUser =
                TargetUser(
                    publicKey = receiverAddress,
                ),
            signer = getTransactionSigner(senderAddress),
            isArc59Transaction = false,
        )
    }

    fun setup(lifecycle: Lifecycle) {
        transactionSignManager.setup(lifecycle)
    }

    fun sendTransaction() {
        viewModelScope.launch {
            val transactionData = createSendTransactionData() ?: return@launch
            stateDelegate.updateState { ViewState.Confirming }
            transactionSignManager.initSigningTransactions(
                isGroupTransaction = false,
                transactionData,
            )
        }
    }

    private fun sendSignedTransaction(signedTransactionDetail: SignedTransactionDetail) {
        viewModelScope.launch(Dispatchers.IO) {
            sendSignedTransactionUseCase
                .sendSignedTransaction(signedTransactionDetail = signedTransactionDetail)
                .collectLatest {
                    it.useSuspended(
                        onSuccess = { transactionId ->
                            eventDelegate.sendEvent(ViewEvent.TransactionSuccess(transactionId))
                            println("SendSignedTransaction onSuccess: $transactionId")
                        },
                        onFailed = { error ->
                            println("sendSignedTransaction Failed: ${error.exception?.message}")
                            eventDelegate.sendEvent(
                                ViewEvent.ShowError(
                                    error.exception?.message ?: "Transaction failed",
                                ),
                            )
                        },
                    )
                }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState

        data object Confirming : ViewState

        data class Content(
            val senderAddress: String,
            val receiverAddress: String,
            val amount: String,
            val accountBalance: String?,
            val fee: String = "0.001",
            val note: String = ""
        ) : ViewState

        data class Error(
            val message: String,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data class ShowError(
            val message: String,
        ) : ViewEvent

        data class TransactionSuccess(
            val transactionId: String,
        ) : ViewEvent
    }
}
