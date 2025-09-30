package com.michaeltchuang.walletsdk.settings.domain

import com.michaeltchuang.walletsdk.settings.presentation.screens.AlgorandNetwork
import kotlinx.coroutines.flow.Flow

expect class NodePreferenceRepository() {
    fun getSavedNodePreferenceFlow(): Flow<AlgorandNetwork>

    suspend fun saveNodePreference(network: AlgorandNetwork)
}

expect fun provideNodePreferenceRepository(): NodePreferenceRepository
