package com.michaeltchuang.walletsdk.foundation.cache

import kotlinx.coroutines.flow.Flow

internal interface LocalCache<KEY, VALUE> {
    fun getCacheFlow(): Flow<HashMap<KEY, VALUE>>

    fun put(key: KEY, value: VALUE)
    fun putAll(pairs: List<Pair<KEY, VALUE>>)

    operator fun get(key: KEY): VALUE?
    fun getAll(): List<VALUE>

    fun delete(key: KEY)

    fun clear()
}
