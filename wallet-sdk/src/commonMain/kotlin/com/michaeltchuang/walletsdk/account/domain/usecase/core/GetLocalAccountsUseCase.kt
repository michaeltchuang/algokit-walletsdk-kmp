package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.repository.local.Algo25AccountRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.Falcon24AccountRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdKeyAccountRepository
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccounts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetLocalAccountsUseCase(
    private val falcon24AccountRepository: Falcon24AccountRepository,
    private val hdKeyAccountRepository: HdKeyAccountRepository,
    private val algo25AccountRepository: Algo25AccountRepository,
    private val dispatcher: CoroutineDispatcher,
) : GetLocalAccounts {
    override suspend fun invoke(): List<LocalAccount> =
        withContext(dispatcher) {
            val deferredFalcon24Accounts = async { falcon24AccountRepository.getAll() }
            val deferredHdKeyAccounts = async { hdKeyAccountRepository.getAll() }
            val deferredAlgo25Accounts = async { algo25AccountRepository.getAll() }
            awaitAll(
                deferredFalcon24Accounts,
                deferredHdKeyAccounts,
                deferredAlgo25Accounts,
            ).flatten()
        }
}
