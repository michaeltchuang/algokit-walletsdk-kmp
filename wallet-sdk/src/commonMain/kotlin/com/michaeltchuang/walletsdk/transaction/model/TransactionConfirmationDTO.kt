

package com.michaeltchuang.walletsdk.transaction.model

data class TransactionConfirmationDTO(
    var applicationIndex: Long?,
    /**
     * The number of the asset's unit that were transferred to the close-to address.
     */
    var assetClosingAmount: Long?,
    /**
     * The asset index if the transaction was found and it created an asset.
     */
    var assetIndex: Long?,
    /**
     * Rewards in microalgos applied to the close remainder to account.
     */
    var closeRewards: Long?,
    /**
     * Closing amount for the transaction.
     */
    var closingAmount: Long?,
    /**
     * The round where this transaction was confirmed, if present.
     */
    var confirmedRound: Long?,
    /**
     * (gd) Global state key/value changes for the application being executed by this
     * transaction.
     */
    // No need for now, if we need, we need to create our domain model for EvalDeltaKeyValue
    // var globalStateDelta: List<EvalDeltaKeyValue>?,
    /**
     * Inner transactions produced by application execution.
     */
    // No need for now, if we need, we need to create our domain model for PendingTransactionResponse
    // which is TransactionConfirmationDTO
    // var innerTxns: List<PendingTransactionResponse>?,
    /**
     * (ld) Local state key/value changes for the application being executed by this
     * transaction.
     */
    // No need for now, if we need, we need to create our domain model for AccountStateDelta
    // var localStateDelta: List<AccountStateDelta>?,
    /**
     * (lg) Logs for the application being executed by this transaction.
     */
    var logs: List<ByteArray>?,
    /**
     * Indicates that the transaction was kicked out of this node's transaction pool
     * (and specifies why that happened). An empty string indicates the transaction
     * wasn't kicked out of this node's txpool due to an error.
     *
     */
    var poolError: String?,
    /**
     * Rewards in microalgos applied to the receiver account.
     */
    var receiverRewards: Long?,
    /**
     * Rewards in microalgos applied to the sender account.
     */
    var senderRewards: Long?,
    /**
     * The raw signed transaction.
     */
    // No need for now, if we need, we need to create our domain model for SignedTransaction
    // var txn: SignedTransaction?,
)
