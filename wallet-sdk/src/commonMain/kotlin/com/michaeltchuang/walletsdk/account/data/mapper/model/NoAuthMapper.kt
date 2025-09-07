package com.michaeltchuang.walletsdk.account.data.mapper.model

import com.michaeltchuang.walletsdk.account.data.database.model.NoAuthEntity
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount

internal interface NoAuthMapper {
    operator fun invoke(entity: NoAuthEntity): LocalAccount.NoAuth
}
