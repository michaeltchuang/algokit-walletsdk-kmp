package com.michaeltchuang.walletsdk.account.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeltchuang.walletsdk.account.data.database.model.NoAuthEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface NoAuthDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: NoAuthEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<NoAuthEntity>)

    @Query("SELECT * FROM no_auth")
    suspend fun getAll(): List<NoAuthEntity>

    @Query("SELECT algo_address FROM no_auth")
    suspend fun getAllAddresses(): List<String>

    @Query("SELECT * FROM no_auth")
    fun getAllAsFlow(): Flow<List<NoAuthEntity>>

    @Query("SELECT COUNT(*) FROM no_auth")
    fun getTableSizeAsFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM no_auth")
    suspend fun getTableSize(): Int

    @Query("SELECT * FROM no_auth WHERE :algoAddress = algo_address")
    suspend fun get(algoAddress: String): NoAuthEntity?

    @Query("DELETE FROM no_auth WHERE :algoAddress = algo_address")
    suspend fun delete(algoAddress: String)

    @Query("DELETE FROM no_auth")
    suspend fun clearAll()

    @Query("SELECT EXISTS(SELECT * FROM no_auth WHERE :address = algo_address)")
    suspend fun isAddressExists(address: String): Boolean
}
