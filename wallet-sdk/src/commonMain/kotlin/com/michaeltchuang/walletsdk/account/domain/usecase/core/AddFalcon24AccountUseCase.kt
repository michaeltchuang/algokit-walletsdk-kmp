package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetAccountCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.local.SaveFalcon24Account

internal class AddFalcon24AccountUseCase(
    private val saveFalcon24Account: SaveFalcon24Account,
    private val setCustomInfo: SetAccountCustomInfo,
) : AddFalcon24Account {
    override suspend fun invoke(
        address: String,
        publicKey: ByteArray,
        privateKey: ByteArray,
        seedId: Int,
        isBackedUp: Boolean,
        customName: String?,
        orderIndex: Int,
    ) {
        val account =
            LocalAccount.Falcon24(
                address,
                seedId,
                publicKey,
            )
        saveFalcon24Account(account, seedId, privateKey)
        setCustomInfo(CustomAccountInfo(address, customName, orderIndex, isBackedUp))
    }
}
