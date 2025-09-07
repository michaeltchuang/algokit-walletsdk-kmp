package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetAccountCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.local.SaveAlgo25Account

internal class AddAlgo25AccountUseCase(
    private val saveAlgo25Account: SaveAlgo25Account,
    private val setCustomInfo: SetAccountCustomInfo,
) : AddAlgo25Account {

    override suspend fun invoke(
        address: String,
        secretKey: ByteArray,
        isBackedUp: Boolean,
        customName: String?,
        orderIndex: Int
    ) {
        val account = LocalAccount.Algo25(address)
        saveAlgo25Account(account, secretKey)
        setCustomInfo(CustomAccountInfo(address, customName, orderIndex, isBackedUp))
    }
}
