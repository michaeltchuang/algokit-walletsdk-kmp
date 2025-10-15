package com.michaeltchuang.walletsdk.core.account.data.repository

import com.michaeltchuang.walletsdk.core.account.data.database.dao.CustomHdSeedInfoDao
import com.michaeltchuang.walletsdk.core.account.data.mapper.entity.CustomHdSeedInfoEntityMapper
import com.michaeltchuang.walletsdk.core.account.data.mapper.model.CustomHdSeedInfoMapper
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.CustomHdSeedInfo
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.HdSeedOrderIndex
import com.michaeltchuang.walletsdk.core.account.domain.repository.custom.CustomHdSeedInfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class CustomHdSeedInfoRepositoryImpl(
    private val customHdSeedInfoDao: CustomHdSeedInfoDao,
    private val customHdSeedInfoMapper: CustomHdSeedInfoMapper,
    private val customHdSeedInfoEntityMapper: CustomHdSeedInfoEntityMapper,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CustomHdSeedInfoRepository {
    override suspend fun getAllCustomInfo(): List<CustomHdSeedInfo> =
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.getAll().map {
                val customHdSeedInfo =
                    CustomHdSeedInfo(
                        seedId = it.seedId,
                        entropyCustomName = it.entropyCustomName,
                        orderIndex = it.orderIndex,
                        isBackedUp = it.isBackedUp,
                    )
                customHdSeedInfo
            }
        }

    override suspend fun getCustomInfo(seedId: Int): CustomHdSeedInfo? =
        withContext(coroutineDispatcher) {
            val customInfoEntity = customHdSeedInfoDao.getOrNull(seedId)
            customInfoEntity?.let { customHdSeedInfoMapper(it) }
        }

    override suspend fun getCustomInfoOrNull(seedId: Int): CustomHdSeedInfo? {
        return withContext(coroutineDispatcher) {
            val customHdSeedInfoEntity =
                customHdSeedInfoDao.getOrNull(seedId) ?: return@withContext null
            customHdSeedInfoMapper(customHdSeedInfoEntity)
        }
    }

    override suspend fun setCustomInfo(info: CustomHdSeedInfo) {
        withContext(coroutineDispatcher) {
            val entity = customHdSeedInfoEntityMapper(info)
            customHdSeedInfoDao.insert(entity)
        }
    }

    override suspend fun setCustomName(
        seedId: Int,
        name: String,
    ) {
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.updateCustomName(seedId, name)
        }
    }

    override suspend fun getCustomName(seedId: Int): String? =
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.getCustomName(seedId)
        }

    override suspend fun deleteCustomInfo(seedId: Int) {
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.delete(seedId)
        }
    }

    override suspend fun getNotBackedUpHdSeeds(): Set<Int> =
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.getNotBackedUpSeedIds().toSet()
        }

    override suspend fun getBackedUpHdSeeds(): Set<Int> =
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.getBackedUpSeedIds().toSet()
        }

    override suspend fun isHdSeedBackedUp(seedId: Int): Boolean =
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.isAccountBackedUp(seedId)
        }

    override suspend fun getAllHdSeedOrderIndexes(): List<HdSeedOrderIndex> =
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.getAll().map {
                HdSeedOrderIndex(it.seedId, it.orderIndex)
            }
        }

    override suspend fun setOrderIndex(
        seedId: Int,
        orderIndex: Int,
    ) {
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.updateOrderIndex(seedId, orderIndex)
        }
    }

    override suspend fun clearAllInformation() {
        withContext(coroutineDispatcher) {
            customHdSeedInfoDao.clearAll()
        }
    }
}
