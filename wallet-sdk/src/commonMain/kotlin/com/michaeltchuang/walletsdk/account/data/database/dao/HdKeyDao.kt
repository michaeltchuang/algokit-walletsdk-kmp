package com.michaeltchuang.walletsdk.account.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeltchuang.walletsdk.account.data.database.model.HdKeyEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface HdKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HdKeyEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<HdKeyEntity>)

    @Query("SELECT * FROM hd_keys")
    suspend fun getAll(): List<HdKeyEntity>

    @Query("SELECT algo_address FROM hd_keys")
    suspend fun getAllAddresses(): List<String>

    @Query("SELECT * FROM hd_keys")
    fun getAllAsFlow(): Flow<List<HdKeyEntity>>

    @Query("SELECT COUNT(*) FROM hd_keys")
    fun getTableSizeAsFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM hd_keys")
    suspend fun getTableSize(): Int

    @Query("SELECT COUNT(*) FROM hd_keys WHERE seed_id = :seedId")
    suspend fun getDerivedAddressCountOfSeed(seedId: Int): Int

    @Query("SELECT * FROM hd_keys WHERE :algoAddress = algo_address")
    suspend fun get(algoAddress: String): HdKeyEntity?

    @Query("SELECT seed_id FROM hd_keys WHERE algo_address = :algoAddress")
    suspend fun getHdSeedId(algoAddress: String): Int?

    @Query("DELETE FROM hd_keys WHERE :algoAddress = algo_address")
    suspend fun delete(algoAddress: String)

    @Query("DELETE FROM hd_keys")
    suspend fun clearAll()
}
