package com.michaeltchuang.walletsdk.foundation.cache

import java.lang.reflect.Type

interface PersistentCacheProvider {
    fun <T : Any> getPersistentCache(
        type: Type,
        key: String,
    ): PersistentCache<T>
}
