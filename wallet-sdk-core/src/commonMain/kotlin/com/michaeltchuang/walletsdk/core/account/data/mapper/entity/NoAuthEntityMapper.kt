package com.michaeltchuang.walletsdk.core.account.data.mapper.entity

import com.michaeltchuang.walletsdk.core.account.data.database.model.NoAuthEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface NoAuthEntityMapper {
    operator fun invoke(localAccount: LocalAccount.NoAuth): NoAuthEntity

    operator fun invoke(address: String): NoAuthEntity
}
