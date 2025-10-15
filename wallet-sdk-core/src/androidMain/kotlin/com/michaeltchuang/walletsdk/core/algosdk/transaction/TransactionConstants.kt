package com.michaeltchuang.walletsdk.core.algosdk.transaction

import java.math.BigInteger

object TransactionConstants {
    val MIN_TXN_FEE: BigInteger = BigInteger.valueOf(1_000L)
    val MIN_REQUIRED_BALANCE_PER_ASSET: BigInteger = BigInteger.valueOf(100_000L)
}
