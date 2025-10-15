package com.michaeltchuang.walletsdk.core.account.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.michaeltchuang.walletsdk.core.account.data.database.model.HdSeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface HdSeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HdSeedEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(entities: List<HdSeedEntity>)

    @Query("SELECT * FROM hd_seeds")
    suspend fun getAll(): List<HdSeedEntity>

    @Query("SELECT * FROM hd_seeds")
    fun getAllAsFlow(): Flow<List<HdSeedEntity>>

    @Query("SELECT COUNT(*) FROM hd_seeds")
    fun getTableSizeAsFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM hd_seeds")
    suspend fun getTableSize(): Int

    @Query("SELECT MAX(seed_id) FROM hd_seeds")
    suspend fun getMaxSeedId(): Int?

    @Query("SELECT EXISTS(SELECT 1 FROM hd_seeds)")
    suspend fun hasAnySeed(): Boolean

    @Query("SELECT * FROM hd_seeds WHERE :seedId = seed_id")
    suspend fun get(seedId: Int): HdSeedEntity?

    @Query("SELECT encrypted_entropy FROM hd_seeds WHERE :seedId = seed_id")
    suspend fun getEncryptedEntropy(seedId: Int): ByteArray?

    @Query("DELETE FROM hd_seeds WHERE :seedId = seed_id")
    suspend fun delete(seedId: Int)

    @Query("DELETE FROM hd_seeds")
    suspend fun clearAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'hd_seeds'")
    suspend fun clearPrimaryKeyIndex()
}
