package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetAccountCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.local.SaveHdKeyAccount


internal class AddHdKeyAccountUseCase(
    private val saveHdKeyAccount: SaveHdKeyAccount,
    private val setCustomInfo: SetAccountCustomInfo
) : AddHdKeyAccount {

    override suspend fun invoke(
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
    ) {
        val account = LocalAccount.HdKey(
            address,
            publicKey,
            seedId,
            account,
            change,
            keyIndex,
            derivationType
        )
        saveHdKeyAccount(account, privateKey)
        setCustomInfo(CustomAccountInfo(address, customName, orderIndex, isBackedUp))
    }
}
