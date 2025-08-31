package com.michaeltchuang.walletsdk.account.domain.model.custom

data class CustomAccountInfo(
    val address: String,
    val customName: String?,
    val orderIndex: Int,
    val isBackedUp: Boolean
)
