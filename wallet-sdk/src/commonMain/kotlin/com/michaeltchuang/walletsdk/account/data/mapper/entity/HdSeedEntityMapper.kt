package com.michaeltchuang.walletsdk.account.data.mapper.entity

import com.michaeltchuang.walletsdk.account.data.database.model.HdSeedEntity

internal interface HdSeedEntityMapper {
    operator fun invoke(seedId: Int, entropy: ByteArray, seed: ByteArray): HdSeedEntity
}
