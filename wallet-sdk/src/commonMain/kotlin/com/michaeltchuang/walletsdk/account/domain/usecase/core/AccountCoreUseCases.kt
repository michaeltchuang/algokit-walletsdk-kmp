package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.foundation.AlgoKitResult

fun interface AddAlgo25Account {
    suspend operator fun invoke(
        address: String,
        secretKey: ByteArray,
        isBackedUp: Boolean,
        customName: String?,
        orderIndex: Int
    )
}

fun interface AddHdSeed {
    suspend operator fun invoke(entropy: ByteArray): AlgoKitResult<Int>
}

fun interface AddHdKeyAccount {
    suspend operator fun invoke(
        address: String,
        publicKey: ByteArray,
        privateKey: ByteArray,
        seedId: Int,
        account: Int,
        change: Int,
        keyIndex: Int,
        derivationType: Int,
        isBackedUp: Boolean,
        customName: String?,
        orderIndex: Int
    )
}
