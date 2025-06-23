

package com.michaeltchuang.walletsdk.algosdk.transaction.usecase

import com.michaeltchuang.walletsdk.algosdk.transaction.model.RawTransaction

fun interface ParseTransactionMessagePack {
    operator fun invoke(txnByteArray: ByteArray): RawTransaction?
}
