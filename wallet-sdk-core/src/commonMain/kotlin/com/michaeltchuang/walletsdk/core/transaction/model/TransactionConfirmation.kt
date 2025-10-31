package com.michaeltchuang.walletsdk.core.transaction.model

data class TransactionConfirmation(
    var applicationIndex: Long?,
    var assetClosingAmount: Long?,
    var assetIndex: Long?,
    var closeRewards: Long?,
    var closingAmount: Long?,
    var confirmedRound: Long?,
    var logs: List<ByteArray>?,
    var poolError: String?,
    var receiverRewards: Long?,
    var senderRewards: Long?,
)
