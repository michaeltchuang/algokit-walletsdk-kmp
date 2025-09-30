package com.michaeltchuang.walletsdk.account.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeltchuang.walletsdk.account.data.database.model.LedgerBleEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface LedgerBleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LedgerBleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<LedgerBleEntity>)

    @Query("SELECT * FROM ledger_ble")
    suspend fun getAll(): List<LedgerBleEntity>

    @Query("SELECT algo_address FROM ledger_ble")
    suspend fun getAllAddresses(): List<String>

    @Query("SELECT * FROM ledger_ble")
    fun getAllAsFlow(): Flow<List<LedgerBleEntity>>

    @Query("SELECT COUNT(*) FROM ledger_ble")
    fun getTableSizeAsFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM ledger_ble")
    suspend fun getTableSize(): Int

    @Query("SELECT * FROM ledger_ble WHERE :algoAddress = algo_address")
    suspend fun get(algoAddress: String): LedgerBleEntity?

    @Query("DELETE FROM ledger_ble WHERE :algoAddress = algo_address")
    suspend fun delete(algoAddress: String)

    @Query("DELETE FROM ledger_ble")
    suspend fun clearAll()
}
