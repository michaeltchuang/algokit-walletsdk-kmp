package com.michaeltchuang.walletsdk.settings.domain

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.michaeltchuang.walletsdk.settings.presentation.screens.AlgorandNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

val Context.nodeDataStore by preferencesDataStore(name = "node_preferences")
private val NODE_KEY = stringPreferencesKey("node_key")

// Global context holder for cases where context is not available in DI
object AndroidContextHolder {
    var applicationContext: Context? = null
}

actual class NodePreferenceRepository actual constructor() {
    private val ctx = AndroidContextHolder.applicationContext

    private fun networkToString(network: AlgorandNetwork): String = network.name

    private fun stringToNetwork(str: String?): AlgorandNetwork = AlgorandNetwork.entries.find { it.name == str } ?: AlgorandNetwork.MAINNET

    actual fun getSavedNodePreferenceFlow(): Flow<AlgorandNetwork> =
        if (ctx != null) {
            ctx.nodeDataStore.data.map { preferences ->
                stringToNetwork(preferences[NODE_KEY])
            }
        } else {
            // Fallback to default when context is not available
            flow { emit(AlgorandNetwork.TESTNET) }
        }

    actual suspend fun saveNodePreference(network: AlgorandNetwork) {
        if (ctx != null) {
            withContext(Dispatchers.IO) {
                ctx.nodeDataStore.edit { prefs ->
                    prefs[NODE_KEY] = networkToString(network)
                }
            }
        }
        // If context is null, we silently ignore the save operation
        // In a production app, you might want to cache this and save it later
    }
}

actual fun provideNodePreferenceRepository(): NodePreferenceRepository = NodePreferenceRepository()
