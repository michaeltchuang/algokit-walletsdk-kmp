package com.michaeltchuang.walletsdk.foundation.database.util

internal object DaoUtils {
    private const val DEFAULT_CHUNK_SIZE = 1000

    internal suspend fun <K, T> smartUpsert(
        newEntity: T,
        getKey: (T) -> K,
        fetchExistingByKey: suspend (K) -> T?,
        upsert: suspend (T) -> Unit,
        diff: (T, T) -> Boolean = { a, b -> a != b },
    ) {
        val existing = fetchExistingByKey(getKey(newEntity))
        if (existing == null || diff(newEntity, existing)) {
            upsert(newEntity)
        }
    }

    internal suspend fun <K, T> smartUpsert(
        newEntities: List<T>,
        getKey: (T) -> K,
        fetchExistingByKeys: suspend (List<K>) -> List<T>,
        upsert: suspend (List<T>) -> Unit,
        diff: (T, T) -> Boolean = { a, b -> a != b },
        chunkSize: Int = DEFAULT_CHUNK_SIZE,
    ) {
        val entitiesToUpdate =
            newEntities.chunked(chunkSize).flatMap { chunk ->
                val existingMap = fetchExistingByKeys(chunk.map(getKey)).associateBy(getKey)
                chunk.mapNotNull { new ->
                    val old = existingMap[getKey(new)]
                    if (old == null || diff(new, old)) new else null
                }
            }
        if (entitiesToUpdate.isNotEmpty()) upsert(entitiesToUpdate)
    }
}
