package com.michaeltchuang.walletsdk.account.domain.repository.custom

import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomHdSeedInfo
import com.michaeltchuang.walletsdk.account.domain.model.custom.HdSeedOrderIndex

internal interface CustomHdSeedInfoRepository {
    suspend fun getAllCustomInfo(): List<CustomHdSeedInfo>

    suspend fun getCustomInfo(seedId: Int): CustomHdSeedInfo?

    suspend fun getCustomInfoOrNull(seedId: Int): CustomHdSeedInfo?

    suspend fun setCustomInfo(info: CustomHdSeedInfo)

    suspend fun setCustomName(
        seedId: Int,
        name: String,
    )

    suspend fun getCustomName(seedId: Int): String?

    suspend fun setOrderIndex(
        seedId: Int,
        orderIndex: Int,
    )

    suspend fun deleteCustomInfo(seedId: Int)

    suspend fun getNotBackedUpHdSeeds(): Set<Int>

    suspend fun getBackedUpHdSeeds(): Set<Int>

    suspend fun isHdSeedBackedUp(seedId: Int): Boolean

    suspend fun getAllHdSeedOrderIndexes(): List<HdSeedOrderIndex>

    suspend fun clearAllInformation()
}
