package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.repository.local.Algo25AccountRepository
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.DeleteAccountCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.local.DeleteAlgo25Account

class DeleteAlgo25AccountUseCase(
    private val algo25AccountRepository: Algo25AccountRepository,
    private val customInfo: DeleteAccountCustomInfo
) : DeleteAlgo25Account {

    override suspend fun invoke(address: String) {
        algo25AccountRepository.deleteAccount(address)
        customInfo.invoke(address)
    }
}
