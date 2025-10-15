package com.michaeltchuang.walletsdk.core.account.domain.model.local

data class HdWalletSummary(
    val seedId: Int,
    val accountCount: Int,
    val maxAccountIndex: Int,
    val primaryValue: String,
    val secondaryValue: String,
)
