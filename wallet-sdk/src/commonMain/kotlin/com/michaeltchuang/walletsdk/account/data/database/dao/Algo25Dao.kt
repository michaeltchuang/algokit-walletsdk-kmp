package com.michaeltchuang.walletsdk.account.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeltchuang.walletsdk.account.data.database.model.Algo25Entity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface Algo25Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: Algo25Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<Algo25Entity>)

    @Query("SELECT * FROM algo_25")
    suspend fun getAll(): List<Algo25Entity>

    @Query("SELECT algo_address FROM algo_25")
    suspend fun getAllAddresses(): List<String>

    @Query("SELECT * FROM algo_25")
    fun getAllAsFlow(): Flow<List<Algo25Entity>>

    @Query("SELECT COUNT(*) FROM algo_25")
    fun getTableSizeAsFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM algo_25")
    suspend fun getTableSize(): Int

    @Query("SELECT * FROM algo_25 WHERE :algoAddress = algo_address")
    suspend fun get(algoAddress: String): Algo25Entity?

    @Query("DELETE FROM algo_25 WHERE :algoAddress = algo_address")
    suspend fun delete(algoAddress: String)

    @Query("DELETE FROM algo_25")
    suspend fun clearAll()
}
