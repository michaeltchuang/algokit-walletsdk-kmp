package com.michaeltchuang.walletsdk.core.deeplink.model

import kotlinx.serialization.Serializable

@Serializable
data class KeyRegTransactionDetail(
    val address: String,
    val type: String,
    val voteKey: String?,
    val selectionPublicKey: String?,
    val sprfkey: String?,
    val voteFirstRound: String?,
    val voteLastRound: String?,
    val voteKeyDilution: String?,
    val fee: String?,
    val note: String?,
    val xnote: String?,
)
