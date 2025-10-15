package com.michaeltchuang.walletsdk.core.account.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeltchuang.walletsdk.core.account.data.database.model.Falcon24Entity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface Falcon24Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Falcon24Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<Falcon24Entity>)

    @Query("SELECT * FROM falcon_24")
    suspend fun getAll(): List<Falcon24Entity>

    @Query("SELECT algo_address FROM falcon_24")
    suspend fun getAllAddresses(): List<String>

    @Query("SELECT * FROM falcon_24")
    fun getAllAsFlow(): Flow<List<Falcon24Entity>>

    @Query("SELECT COUNT(*) FROM falcon_24")
    fun getTableSizeAsFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM falcon_24")
    suspend fun getTableSize(): Int

    @Query("SELECT COUNT(*) FROM falcon_24 WHERE seed_id = :seedId")
    suspend fun getDerivedAddressCountOfSeed(seedId: Int): Int

    @Query("SELECT * FROM falcon_24 WHERE :algoAddress = algo_address")
    suspend fun get(algoAddress: String): Falcon24Entity?

    @Query("DELETE FROM falcon_24 WHERE :algoAddress = algo_address")
    suspend fun delete(algoAddress: String)

    @Query("DELETE FROM falcon_24")
    suspend fun clearAll()
}
