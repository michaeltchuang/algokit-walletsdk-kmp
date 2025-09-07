package com.michaeltchuang.walletsdk.account.domain.repository.custom

import com.michaeltchuang.walletsdk.account.domain.model.custom.AccountOrderIndex
import com.michaeltchuang.walletsdk.account.domain.model.custom.CustomAccountInfo
import kotlinx.coroutines.flow.Flow

internal interface CustomAccountInfoRepository {

    suspend fun getCustomInfo(address: String): CustomAccountInfo

    suspend fun getCustomInfoOrNull(address: String): CustomAccountInfo?

    suspend fun getCustomInfos(addresses: List<String>): Map<String, CustomAccountInfo?>

    fun getCustomInfoFlow(addresses: List<String>): Flow<Map<String, CustomAccountInfo?>>

    suspend fun setCustomInfo(customAccountInfo: CustomAccountInfo)

    suspend fun setCustomName(address: String, name: String)

    suspend fun getCustomName(address: String): String?

    suspend fun setOrderIndex(address: String, orderIndex: Int)

    suspend fun deleteCustomInfo(address: String)

    suspend fun getNotBackedUpAccounts(): Set<String>

    suspend fun getBackedUpAccounts(): Set<String>

    suspend fun setAddressesBackedUp(addresses: Set<String>)

    suspend fun isAccountBackedUp(accountAddress: String): Boolean

    suspend fun getAllAccountOrderIndexes(): List<AccountOrderIndex>

    suspend fun clearAllInformation()
}
