package com.michaeltchuang.walletsdk.account.data.repository

import com.michaeltchuang.walletsdk.account.data.database.dao.CustomAccountInfoDao
import com.michaeltchuang.walletsdk.account.data.mapper.entity.CustomAccountInfoEntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.CustomAccountInfoMapper
import com.michaeltchuang.walletsdk.account.domain.model.custom.AccountOrderIndex
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo
import com.michaeltchuang.walletsdk.account.domain.repository.custom.CustomAccountInfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class CustomAccountInfoRepositoryImpl(
    private val customAccountInfoDao: CustomAccountInfoDao,
    private val customAccountInfoMapper: CustomAccountInfoMapper,
    private val customAccountInfoEntityMapper: CustomAccountInfoEntityMapper,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CustomAccountInfoRepository {
    override suspend fun getCustomInfo(address: String): CustomAccountInfo =
        withContext(coroutineDispatcher) {
            val customInfoEntity = customAccountInfoDao.getOrNull(address)
            customAccountInfoMapper(address, customInfoEntity)
        }

    override suspend fun getCustomInfoOrNull(address: String): CustomAccountInfo? {
        return withContext(coroutineDispatcher) {
            val customInfoEntity =
                customAccountInfoDao.getOrNull(address) ?: return@withContext null
            customAccountInfoMapper(address, customInfoEntity)
        }
    }

    override suspend fun getCustomInfos(addresses: List<String>): Map<String, CustomAccountInfo?> =
        withContext(coroutineDispatcher) {
            val entityList = customAccountInfoDao.getAll(addresses)
            entityList.associate {
                it.algoAddress to customAccountInfoMapper(it.algoAddress, it)
            }
        }

    override fun getCustomInfoFlow(addresses: List<String>): Flow<Map<String, CustomAccountInfo?>> =
        customAccountInfoDao.getAllAsFlow(addresses).map { entityList ->
            entityList.associate {
                it.algoAddress to customAccountInfoMapper(it.algoAddress, it)
            }
        }

    override suspend fun setCustomInfo(customAccountInfo: CustomAccountInfo) {
        withContext(coroutineDispatcher) {
            val entity = customAccountInfoEntityMapper(customAccountInfo)
            customAccountInfoDao.insert(entity)
        }
    }

    override suspend fun setCustomName(
        address: String,
        name: String,
    ) {
        withContext(coroutineDispatcher) {
            customAccountInfoDao.updateCustomName(address, name)
        }
    }

    override suspend fun getCustomName(address: String): String? =
        withContext(coroutineDispatcher) {
            customAccountInfoDao.getCustomName(address)
        }

    override suspend fun deleteCustomInfo(address: String) {
        withContext(coroutineDispatcher) {
            customAccountInfoDao.delete(address)
        }
    }

    override suspend fun getNotBackedUpAccounts(): Set<String> =
        withContext(coroutineDispatcher) {
            customAccountInfoDao.getNotBackedUpAddresses().toSet()
        }

    override suspend fun getBackedUpAccounts(): Set<String> =
        withContext(coroutineDispatcher) {
            customAccountInfoDao.getBackedUpAddresses().toSet()
        }

    override suspend fun setAddressesBackedUp(addresses: Set<String>) =
        withContext(coroutineDispatcher) {
            customAccountInfoDao.setAddressesBackedUp(addresses.toList())
        }

    override suspend fun isAccountBackedUp(accountAddress: String): Boolean =
        withContext(coroutineDispatcher) {
            customAccountInfoDao.isAccountBackedUp(accountAddress)
        }

    override suspend fun getAllAccountOrderIndexes(): List<AccountOrderIndex> =
        withContext(coroutineDispatcher) {
            customAccountInfoDao.getAll().map {
                AccountOrderIndex(it.algoAddress, it.orderIndex)
            }
        }

    override suspend fun setOrderIndex(
        address: String,
        orderIndex: Int,
    ) {
        withContext(coroutineDispatcher) {
            customAccountInfoDao.updateOrderIndex(address, orderIndex)
        }
    }

    override suspend fun clearAllInformation() {
        withContext(coroutineDispatcher) {
            customAccountInfoDao.clearAll()
        }
    }
}
