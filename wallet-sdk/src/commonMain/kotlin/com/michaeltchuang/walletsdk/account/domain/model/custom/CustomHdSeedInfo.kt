package com.michaeltchuang.walletsdk.account.domain.model.custom

data class CustomHdSeedInfo(
    val seedId: Int,
    val entropyCustomName: String,
    val orderIndex: Int,
    val isBackedUp: Boolean,
)
