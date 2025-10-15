package com.michaeltchuang.walletsdk.core.account.domain.usecase.core

import com.michaeltchuang.walletsdk.core.account.domain.repository.local.Falcon24AccountRepository
import com.michaeltchuang.walletsdk.core.account.domain.repository.local.HdKeyAccountRepository
import com.michaeltchuang.walletsdk.core.account.domain.repository.local.HdSeedRepository
import com.michaeltchuang.walletsdk.core.account.domain.usecase.custom.DeleteAccountCustomInfo
import com.michaeltchuang.walletsdk.core.account.domain.usecase.custom.DeleteHdSeedCustomInfo
import com.michaeltchuang.walletsdk.core.account.domain.usecase.local.DeleteFalcon24Account

class DeleteFalcon24AccountUseCase(
    private val falcon24AccountRepository: Falcon24AccountRepository,
    private val customInfo: DeleteAccountCustomInfo,
    private val hdKeyAccountRepository: HdKeyAccountRepository,
    private val deleteHdSeedCustomInfo: DeleteHdSeedCustomInfo,
    private val hdSeedRepository: HdSeedRepository,
) : DeleteFalcon24Account {
    override suspend fun invoke(address: String) {
        deleteFalcon24Account(address)
    }

    private suspend fun deleteFalcon24Account(address: String) {
        val falcon24 = falcon24AccountRepository.getAccount(address) ?: return
        falcon24AccountRepository.deleteAccount(address)
        deleteHdSeedCustomInfo.invoke(falcon24.seedId)
        val derivedAddressesCount =
            hdKeyAccountRepository.getDerivedAddressCountOfSeed(falcon24.seedId)
        val falcon24Count =
            falcon24AccountRepository.getDerivedAddressCountOfSeed(falcon24.seedId)
        if (derivedAddressesCount == 0 && falcon24Count == 0) {
            hdSeedRepository.deleteHdSeed(falcon24.seedId)
        }
    }
}
