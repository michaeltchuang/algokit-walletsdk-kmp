package com.michaeltchuang.walletsdk.account.data.repository

import com.michaeltchuang.walletsdk.account.data.database.dao.HdKeyDao
import com.michaeltchuang.walletsdk.account.data.mapper.entity.HdKeyEntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdKeyMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdWalletSummaryMapper
import com.michaeltchuang.walletsdk.account.domain.model.local.HdWalletSummary
import com.michaeltchuang.walletsdk.account.domain.model.local.LocalAccount.HdKey
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdKeyAccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class HdKeyAccountRepositoryImpl(
    private val hdKeyDao: HdKeyDao,
    private val hdKeyEntityMapper: HdKeyEntityMapper,
    private val hdWalletSummaryMapper: HdWalletSummaryMapper,
    private val hdKeyMapper: HdKeyMapper,
   /* private val aesPlatformManager: AESPlatformManager,*/
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : HdKeyAccountRepository {

    override fun getAllAsFlow(): Flow<List<HdKey>> {
        return hdKeyDao.getAllAsFlow().map { entityList ->
            entityList.map { entity -> hdKeyMapper(entity) }
        }
    }

    override fun getAccountCountAsFlow(): Flow<Int> {
        return hdKeyDao.getTableSizeAsFlow()
    }

    override suspend fun getAccountCount(): Int {
        return hdKeyDao.getTableSize()
    }

    override suspend fun getAll(): List<HdKey> {
        return withContext(coroutineDispatcher) {
            val hdKeyEntities = hdKeyDao.getAll()
            hdKeyEntities.map { hdKeyMapper(it) }
        }
    }

    override suspend fun getAllAddresses(): List<String> {
        return withContext(coroutineDispatcher) {
            hdKeyDao.getAllAddresses()
        }
    }

    override suspend fun getAccount(address: String): HdKey? {
        return withContext(coroutineDispatcher) {
            hdKeyDao.get(address)?.let { hdKeyMapper(it) }
        }
    }

    override suspend fun getDerivedAddressCountOfSeed(seedId: Int): Int {
        return withContext(coroutineDispatcher) {
            hdKeyDao.getDerivedAddressCountOfSeed(seedId)
        }
    }

    override suspend fun addAccount(account: HdKey, privateKey: ByteArray) {
        withContext(coroutineDispatcher) {
            val hdKeyEntity = hdKeyEntityMapper(account, privateKey)
            hdKeyDao.insert(hdKeyEntity)
        }
    }

    override suspend fun deleteAccount(address: String) {
        withContext(coroutineDispatcher) {
            hdKeyDao.delete(address)
        }
    }

    override suspend fun deleteAllAccounts() {
        withContext(coroutineDispatcher) {
            hdKeyDao.clearAll()
        }
    }

    override suspend fun getPrivateKey(address: String): ByteArray? {
        return withContext(coroutineDispatcher) {
            val encryptedSK = hdKeyDao.get(address)?.encryptedPrivateKey
           // encryptedSK?.let { aesPlatformManager.decryptByteArray(it) }
            ByteArray(0)
        }
    }

    override suspend fun getHdWalletSummaries(): List<HdWalletSummary> {
        return withContext(coroutineDispatcher) {
            val hdKeyEntities = hdKeyDao.getAll()

            val uniqueHdKeyEntities = hdKeyEntities.groupBy { it.seedId }
                .mapNotNull { (_, group) -> group.maxByOrNull { it.account } }

            uniqueHdKeyEntities.map { uniqueHdKeyEntity ->
                val accountCount = hdKeyEntities.count { uniqueHdKeyEntity.seedId == it.seedId }
                hdWalletSummaryMapper(uniqueHdKeyEntity, accountCount)
            }
        }
    }

    override suspend fun getHdSeedId(address: String): Int? {
        return withContext(coroutineDispatcher) {
            hdKeyDao.getHdSeedId(address)
        }
    }
}
