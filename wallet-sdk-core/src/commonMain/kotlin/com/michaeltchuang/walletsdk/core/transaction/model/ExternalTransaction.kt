package com.michaeltchuang.walletsdk.core.transaction.model

/**
 * This interface is being used for the transactions that we didn't create but need to be signed by the user
 */
interface ExternalTransaction {
    val transactionByteArray: ByteArray?
    val accountAddress: String
    val accountAuthAddress: String?
    val isRekeyedToAnotherAccount: Boolean
}
