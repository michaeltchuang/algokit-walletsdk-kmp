package com.michaeltchuang.walletsdk.core.algosdk.transaction.usecase

import com.michaeltchuang.walletsdk.core.algosdk.transaction.model.RawTransaction

fun interface ParseTransactionMessagePack {
    operator fun invoke(txnByteArray: ByteArray): RawTransaction?
}
