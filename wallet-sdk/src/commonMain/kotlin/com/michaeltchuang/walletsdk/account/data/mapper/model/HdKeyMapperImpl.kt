package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.HdKeyEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal class HdKeyMapperImpl : HdKeyMapper {
    override fun invoke(entity: HdKeyEntity): LocalAccount.HdKey {
        return LocalAccount.HdKey(
            algoAddress = entity.algoAddress,
            publicKey = entity.publicKey,
            seedId = entity.seedId,
            account = entity.account,
            change = entity.change,
            keyIndex = entity.keyIndex,
            derivationType = entity.derivationType
        )
    }
}
