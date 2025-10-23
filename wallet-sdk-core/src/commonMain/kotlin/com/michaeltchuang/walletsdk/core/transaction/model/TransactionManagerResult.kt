package com.michaeltchuang.walletsdk.core.transaction.model

sealed class TransactionManagerResult {
    data class Success(val signedTransactionDetail: SignedTransactionDetail) :
        TransactionManagerResult()

    sealed class Error : TransactionManagerResult() {

        sealed class GlobalWarningError : Error() {

            fun getMessage(): String =
                when (this) {
                    is Defined -> { error}
                    is MinBalanceError -> "MinBalanceError"
                    is Api -> "Api Error"
                }


           data class Defined(val error: String = "Defined Error") : GlobalWarningError()

            class Api() : GlobalWarningError()

            class MinBalanceError() : GlobalWarningError()
        }
    }

    object LedgerOperationCanceled : TransactionManagerResult()

    object Loading : TransactionManagerResult()

    data class LedgerWaitingForApproval(val bluetoothName: String?) : TransactionManagerResult()

    object LedgerScanFailed : TransactionManagerResult()
}