package com.michaeltchuang.walletsdk.core.account.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeltchuang.walletsdk.core.account.data.database.model.CustomAccountInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CustomAccountInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CustomAccountInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<CustomAccountInfoEntity>)

    @Query("SELECT * FROM custom_account_info")
    suspend fun getAll(): List<CustomAccountInfoEntity>

    @Query("SELECT * FROM custom_account_info WHERE algo_address IN (:addresses)")
    suspend fun getAll(addresses: List<String>): List<CustomAccountInfoEntity>

    @Query("SELECT * FROM custom_account_info WHERE  algo_address IN (:addresses)")
    fun getAllAsFlow(addresses: List<String>): Flow<List<CustomAccountInfoEntity>>

    @Query("SELECT * FROM custom_account_info WHERE :address = algo_address")
    suspend fun get(address: String): CustomAccountInfoEntity

    @Query("SELECT * FROM custom_account_info WHERE :address = algo_address")
    suspend fun getOrNull(address: String): CustomAccountInfoEntity?

    @Query("DELETE FROM custom_account_info WHERE :address = algo_address")
    suspend fun delete(address: String)

    @Query("UPDATE custom_account_info SET custom_name = :customName WHERE :address = algo_address")
    suspend fun updateCustomName(
        address: String,
        customName: String,
    )

    @Query("UPDATE custom_account_info SET order_index = :orderIndex WHERE :address = algo_address")
    suspend fun updateOrderIndex(
        address: String,
        orderIndex: Int,
    )

    @Query("DELETE FROM custom_account_info")
    suspend fun clearAll()

    @Query("SELECT algo_address FROM custom_account_info WHERE is_backed_up = 0")
    suspend fun getNotBackedUpAddresses(): List<String>

    @Query("SELECT algo_address FROM custom_account_info WHERE is_backed_up = 1")
    suspend fun getBackedUpAddresses(): List<String>

    @Query("UPDATE custom_account_info SET is_backed_up = 1 WHERE algo_address IN (:addresses)")
    suspend fun setAddressesBackedUp(addresses: List<String>)

    @Query("SELECT is_backed_up FROM custom_account_info WHERE :encryptedAddress = algo_address")
    suspend fun isAccountBackedUp(encryptedAddress: String): Boolean

    @Query("SELECT custom_name FROM custom_account_info WHERE :address = algo_address")
    suspend fun getCustomName(address: String): String?
}
