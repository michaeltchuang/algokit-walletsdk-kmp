package com.michaeltchuang.walletsdk.core.network.domain

import com.michaeltchuang.walletsdk.core.network.model.AlgorandNetwork
import kotlinx.coroutines.flow.Flow

expect class NodePreferenceRepository() {
    fun getSavedNodePreferenceFlow(): Flow<AlgorandNetwork>

    suspend fun saveNodePreference(network: AlgorandNetwork)
}

expect fun provideNodePreferenceRepository(): NodePreferenceRepository
