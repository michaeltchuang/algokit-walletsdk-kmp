package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.repository.local.HdKeyAccountRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdSeedRepository
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.DeleteHdSeedCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.local.DeleteHdKeyAccount

class DeleteHdKeyAccountUseCase(
    private val hdKeyAccountRepository: HdKeyAccountRepository,
    private val deleteHdSeedCustomInfo: DeleteHdSeedCustomInfo,
    private val hdSeedRepository: HdSeedRepository,
) : DeleteHdKeyAccount {
    override suspend fun invoke(address: String) {
        deleteHdKeyAccount(address)
    }

    private suspend fun deleteHdKeyAccount(address: String) {
        val hdKey = hdKeyAccountRepository.getAccount(address) ?: return
        hdKeyAccountRepository.deleteAccount(address)
        deleteHdSeedCustomInfo.invoke(hdKey.seedId)
        val derivedAddressesCount =
            hdKeyAccountRepository.getDerivedAddressCountOfSeed(hdKey.seedId)
        if (derivedAddressesCount == 0) {
            hdSeedRepository.deleteHdSeed(hdKey.seedId)
        }
    }
}
