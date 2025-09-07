package com.michaeltchuang.walletsdk.account.domain.usecase.core

import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount
import com.michaeltchuang.walletsdk.account.domain.repository.local.Algo25AccountRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdKeyAccountRepository
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccounts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.withContext

class GetLocalAccountUseCase(
    private val hdKeyAccountRepository: HdKeyAccountRepository,
    private val algo25AccountRepository: Algo25AccountRepository,
    private val dispatcher: CoroutineDispatcher
) : GetLocalAccounts {

    override suspend fun invoke(): List<LocalAccount> {
        return withContext(dispatcher) {
            val deferredHdKeyAccounts = async { hdKeyAccountRepository.getAll() }
            val deferredAlgo25Accounts = async { algo25AccountRepository.getAll() }
            awaitAll(
                deferredHdKeyAccounts,
                deferredAlgo25Accounts
            ).flatten()
        }
    }
}
