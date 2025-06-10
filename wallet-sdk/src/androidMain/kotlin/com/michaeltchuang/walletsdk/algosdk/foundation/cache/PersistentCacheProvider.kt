 

package com.michaeltchuang.walletsdk.algosdk.foundation.cache

import java.lang.reflect.Type

interface PersistentCacheProvider {
    fun <T: Any> getPersistentCache(type: Type, key: String): PersistentCache<T>
}
