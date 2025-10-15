package com.michaeltchuang.walletsdk.network.domain

import com.michaeltchuang.walletsdk.network.model.AlgorandNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import platform.Foundation.NSUserDefaults
import platform.Foundation.setValue

private const val NODE_KEY = "node_key"

actual class NodePreferenceRepository actual constructor() {
    private val stateFlow = MutableStateFlow(AlgorandNetwork.MAINNET)
    private val defaults: NSUserDefaults = NSUserDefaults.standardUserDefaults

    init {
        val saved = defaults.objectForKey(NODE_KEY) as? String
        stateFlow.value = saved?.let { name ->
            AlgorandNetwork.entries.find { it.name == name }
        } ?: AlgorandNetwork.TESTNET
    }

    actual fun getSavedNodePreferenceFlow(): Flow<AlgorandNetwork> = stateFlow.asStateFlow()

    actual suspend fun saveNodePreference(network: AlgorandNetwork) {
        withContext(Dispatchers.Default) {
            defaults.setValue(network.name, forKey = NODE_KEY)
            stateFlow.value = network
        }
    }
}

actual fun provideNodePreferenceRepository(): NodePreferenceRepository = NodePreferenceRepository()
