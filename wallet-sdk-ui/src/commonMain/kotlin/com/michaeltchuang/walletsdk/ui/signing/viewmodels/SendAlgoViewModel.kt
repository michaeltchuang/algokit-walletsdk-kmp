package com.michaeltchuang.walletsdk.ui.signing.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ionspin.kotlin.bignum.integer.BigInteger
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.GetAccountAlgoBalance
import com.michaeltchuang.walletsdk.core.foundation.EventDelegate
import com.michaeltchuang.walletsdk.core.foundation.EventViewModel
import com.michaeltchuang.walletsdk.core.foundation.StateDelegate
import com.michaeltchuang.walletsdk.core.foundation.StateViewModel
import kotlinx.coroutines.launch

class SendAlgoViewModel(
    private val getAccountAlgoBalance: GetAccountAlgoBalance,
    private val stateDelegate: StateDelegate<ViewState>,
    private val eventDelegate: EventDelegate<ViewEvent>,
) : ViewModel(),
    StateViewModel<SendAlgoViewModel.ViewState> by stateDelegate,
    EventViewModel<SendAlgoViewModel.ViewEvent> by eventDelegate {
    private var accountBalance: BigInteger = BigInteger.ZERO
    private val algoUsdPrice: Double = 0.199 // Mock price, should come from a price service

    init {
        stateDelegate.setDefaultState(ViewState.Loading)
        updateContentState()
    }

    fun fetchAccountBalance(senderAddress: String) {
        viewModelScope.launch {
            try {
                val balance = getAccountAlgoBalance(senderAddress) ?: BigInteger.ZERO
                accountBalance = balance
                updateContentState()
            } catch (e: Exception) {
                stateDelegate.updateState { ViewState.Error("Failed to fetch account balance: ${e.message}") }
            }
        }
    }

    private fun updateContentState() {
        val currentState = stateDelegate.state.value
        val currentAmount = if (currentState is ViewState.Content) currentState.amount else ""

        val balanceInAlgos = accountBalance.toString().toDouble() / 1_000_000.0
        val balanceFormatted =
            if (currentState is ViewState.Content) balanceInAlgos.toString().take(6) else null
        val balanceUsdValue = "$${(balanceInAlgos * algoUsdPrice).toString().take(6)}"

        val amountUsdValue =
            if (currentAmount.isNotEmpty() && currentAmount != "0") {
                val amountDouble = currentAmount.toDoubleOrNull() ?: 0.0
                "$${(amountDouble * algoUsdPrice).toString().take(6)}"
            } else {
                "$0.00"
            }

        stateDelegate.updateState {
            ViewState.Content(
                amount = currentAmount,
                usdValue = amountUsdValue,
                balance = balanceFormatted,
                assetUsdValue = balanceUsdValue,
            )
        }
    }

    fun onDigitPressed(digit: String) {
        val currentState = stateDelegate.state.value
        if (currentState is ViewState.Content) {
            val currentAmount = currentState.amount
            val newAmount =
                when (digit) {
                    "." -> {
                        if (!currentAmount.contains(".")) {
                            if (currentAmount.isEmpty()) "0." else "$currentAmount."
                        } else {
                            currentAmount
                        }
                    }

                    else -> {
                        if (currentAmount == "0") {
                            digit
                        } else {
                            "$currentAmount$digit"
                        }
                    }
                }

            // Validate decimal places (max 6 for microAlgos precision)
            if (newAmount.contains(".")) {
                val parts = newAmount.split(".")
                if (parts.size == 2 && parts[1].length > 6) {
                    return // Don't allow more than 6 decimal places
                }
            }

            updateAmountAndRefresh(newAmount)
        }
    }

    fun onDeletePressed() {
        val currentState = stateDelegate.state.value
        if (currentState is ViewState.Content) {
            val currentAmount = currentState.amount
            val newAmount =
                if (currentAmount.isNotEmpty()) {
                    currentAmount.dropLast(1)
                } else {
                    ""
                }
            updateAmountAndRefresh(newAmount)
        }
    }

    fun onMaxPressed() {
        val maxSendable = accountBalance
        if (maxSendable > BigInteger.ZERO) {
            val maxInAlgos = maxSendable.toString().toDouble() / 1_000_000.0
            val maxFormatted =
                maxInAlgos
                    .toString()
                    .take(8)
                    .trimEnd('0')
                    .trimEnd('.')
            updateAmountAndRefresh(maxFormatted)
        } else {
            updateAmountAndRefresh("0")
        }
    }

    fun onNextPressed() {
        val currentState = stateDelegate.state.value
        if (currentState is ViewState.Content && currentState.amount.isNotEmpty() && currentState.amount != "0") {
            try {
                val amountDouble = currentState.amount.toDouble()
                val amountInMicroAlgos = (amountDouble * 1_000_000).toLong().toString()
                viewModelScope.launch {
                    eventDelegate.sendEvent(ViewEvent.NavigateNext(amountInMicroAlgos))
                }
            } catch (e: Exception) {
                stateDelegate.updateState { ViewState.Error("Invalid amount format") }
            }
        }
    }

    private fun updateAmountAndRefresh(newAmount: String) {
        val currentState = stateDelegate.state.value
        if (currentState is ViewState.Content) {
            val amountUsdValue =
                if (newAmount.isNotEmpty() && newAmount != "0") {
                    val amountDouble = newAmount.toDoubleOrNull() ?: 0.0
                    "$${(amountDouble * algoUsdPrice).toString().take(6)}"
                } else {
                    "$0.00"
                }

            stateDelegate.updateState {
                currentState.copy(
                    amount = newAmount,
                    usdValue = amountUsdValue,
                )
            }
        }
    }

    sealed interface ViewState {
        data object Loading : ViewState

        data class Content(
            val amount: String,
            val usdValue: String,
            val balance: String?,
            val assetUsdValue: String?,
            val showUSDAmount: Boolean = false,
        ) : ViewState

        data class Error(
            val message: String,
        ) : ViewState
    }

    sealed interface ViewEvent {
        data class NavigateNext(
            val amount: String, // Amount in microAlgos
        ) : ViewEvent
    }
}
