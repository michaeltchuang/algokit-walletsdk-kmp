package com.michaeltchuang.walletsdk.core.account.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.michaeltchuang.walletsdk.core.account.data.database.model.Algo25Entity
import com.michaeltchuang.walletsdk.core.account.data.database.model.NoAuthEntity

@Dao
internal interface Algo25NoAuthDao {
    @Upsert
    suspend fun upsertNoAuthEntities(entities: List<NoAuthEntity>)

    @Query("DELETE FROM algo_25 WHERE algo_address IN (:algoAddresses)")
    suspend fun deleteAlgo25Entities(algoAddresses: List<String>)

    @Query("SELECT * FROM algo_25")
    suspend fun getAllAlgo25Entities(): List<Algo25Entity>

    @Transaction
    suspend fun updateAlgo25AccountsToNoAuthAccounts(entities: List<NoAuthEntity>) {
        deleteAlgo25Entities(entities.map { it.algoAddress })
        upsertNoAuthEntities(entities)
    }
}
