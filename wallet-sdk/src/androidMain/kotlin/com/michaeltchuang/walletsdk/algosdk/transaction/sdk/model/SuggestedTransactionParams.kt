

package com.michaeltchuang.walletsdk.algosdk.transaction.sdk.model

data class SuggestedTransactionParams(
    val genesisHash: String,
    val genesisId: String,
    val lastRound: Long,
    val minFee: Long?,
    val fee: TransactionFee,
) {
    data class TransactionFee(
        val fee: Long,
        val type: FeeType,
    ) {
        sealed interface FeeType {
            data object Suggested : FeeType

            data object Flat : FeeType
        }
    }
}
