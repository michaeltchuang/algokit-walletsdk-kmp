package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.HdSeedEntity

internal interface HdSeedEntityMapper {
    operator fun invoke(
        seedId: Int,
        entropy: ByteArray,
        seed: ByteArray,
    ): HdSeedEntity
}
