package com.michaeltchuang.walletsdk.core.transaction.signmanager

sealed class ExternalTransactionSignResult {
    data class Success<T>(
        val signedTransaction: List<T>,
        val signedTransactionsByteArray: List<ByteArray?>? = null,
    ) : ExternalTransactionSignResult()

    sealed class Error : ExternalTransactionSignResult() {
        fun getMessage(): String =
            when (this) {
                is Defined -> "Error"
                is Api -> "Api Error"
            }

        class Defined : Error()

        class Api : Error()
    }

    data class TransactionCancelled(
        val error: Error = Error.Defined(),
    ) : ExternalTransactionSignResult()

    object Loading : ExternalTransactionSignResult()

    data class LedgerWaitingForApproval(
        val ledgerName: String?,
        val currentTransactionIndex: Int?,
        val totalTransactionCount: Int?,
        val isTransactionIndicatorVisible: Boolean,
    ) : ExternalTransactionSignResult()

    object LedgerScanFailed : ExternalTransactionSignResult()

    object NotInitialized : ExternalTransactionSignResult()
}
