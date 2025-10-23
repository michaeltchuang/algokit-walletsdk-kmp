package com.michaeltchuang.walletsdk.core.transaction.model

import com.ionspin.kotlin.bignum.integer.BigInteger


// this user is targetted to be sent.
data class TargetUser(
    val publicKey: String,
    val minBalance: BigInteger? = null,
    val algoBalance: BigInteger? = null,
    val nftDomainAddress: String? = null,
    val nftDomainServiceLogoUrl: String? = null,
)
