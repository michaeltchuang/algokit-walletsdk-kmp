package com.michaeltchuang.walletsdk.core.account.data.mapper.model

import com.michaeltchuang.walletsdk.core.account.data.database.model.NoAuthEntity
import com.michaeltchuang.walletsdk.core.account.domain.model.local.LocalAccount

internal interface NoAuthMapper {
    operator fun invoke(entity: NoAuthEntity): LocalAccount.NoAuth
}
