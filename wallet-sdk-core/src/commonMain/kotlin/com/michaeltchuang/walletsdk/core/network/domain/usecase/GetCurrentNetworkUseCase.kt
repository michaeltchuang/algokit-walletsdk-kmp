package com.michaeltchuang.walletsdk.core.network.domain.usecase

import com.michaeltchuang.walletsdk.core.network.domain.NodePreferenceRepository
import com.michaeltchuang.walletsdk.core.network.model.AlgorandNetwork
import kotlinx.coroutines.flow.Flow

class GetCurrentNetworkUseCase(
    private val nodePreferenceRepository: NodePreferenceRepository,
) {
    operator fun invoke(): Flow<AlgorandNetwork> = nodePreferenceRepository.getSavedNodePreferenceFlow()
}
