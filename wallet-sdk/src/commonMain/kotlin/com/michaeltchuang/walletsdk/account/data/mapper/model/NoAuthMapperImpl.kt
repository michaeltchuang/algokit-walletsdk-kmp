package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.NoAuthEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal class NoAuthMapperImpl : NoAuthMapper {

    override fun invoke(entity: NoAuthEntity): LocalAccount.NoAuth {
        return LocalAccount.NoAuth(
            algoAddress = entity.algoAddress
        )
    }
}
