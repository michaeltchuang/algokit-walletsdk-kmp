package com.michaeltchuang.walletsdk.core.transaction.model
import com.ionspin.kotlin.bignum.integer.BigInteger

sealed class SignedTransactionDetail {
    abstract val signedTransactionData: ByteArray

    open var shouldWaitForConfirmation: Boolean = false

    data class Send(
        override val signedTransactionData: ByteArray,
        val amount: BigInteger,
        val senderAccountName: String,
        val senderAccountAddress: String,
        // val targetUser: TargetUser,
        val isMax: Boolean,
        var fee: Long,
        val assetId: Long,
        val note: String? = null,
        val xnote: String? = null,
    ) : SignedTransactionDetail()

    sealed class AssetOperation : SignedTransactionDetail() {
        abstract val senderAccountAddress: String
        abstract val assetId: Long

        data class AssetAddition(
            override val signedTransactionData: ByteArray,
            override val senderAccountAddress: String,
            override val assetId: Long,
        ) : AssetOperation()

        data class AssetRemoval(
            override val signedTransactionData: ByteArray,
            override val senderAccountAddress: String,
            override val assetId: Long,
        ) : AssetOperation()
    }

    data class RekeyOperation(
        override val signedTransactionData: ByteArray,
        val accountAddress: String,
        val accountName: String,
        val rekeyAdminAddress: String,
    ) : SignedTransactionDetail()

    data class Group(
        override val signedTransactionData: ByteArray,
        val transactions: List<SignedTransactionDetail>?,
    ) : SignedTransactionDetail()

    data class ExternalTransaction(
        override val signedTransactionData: ByteArray,
    ) : SignedTransactionDetail()

    data class Arc59Send(
        override val signedTransactionData: ByteArray,
    ) : SignedTransactionDetail()

    data class Arc59OptIn(
        override val signedTransactionData: ByteArray,
    ) : SignedTransactionDetail() {
        override var shouldWaitForConfirmation: Boolean = true
    }

    data class Arc59ClaimOrReject(
        override val signedTransactionData: ByteArray,
    ) : SignedTransactionDetail()
}
