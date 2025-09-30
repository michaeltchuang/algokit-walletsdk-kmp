package com.michaeltchuang.walletsdk.foundation.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

internal fun getAlgoKitDatabase(): AlgoKitDatabase = createAlgoKitDatabase().build()

internal fun createAlgoKitDatabase(): RoomDatabase.Builder<AlgoKitDatabase> {
    val dbFilePath = documentDirectory() + "/${AlgoKitDatabase.DATABASE_NAME}.db"
    return Room
        .databaseBuilder<AlgoKitDatabase>(
            name = dbFilePath,
        ).setDriver(BundledSQLiteDriver())
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory =
        NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
    return requireNotNull(documentDirectory?.path)
}
