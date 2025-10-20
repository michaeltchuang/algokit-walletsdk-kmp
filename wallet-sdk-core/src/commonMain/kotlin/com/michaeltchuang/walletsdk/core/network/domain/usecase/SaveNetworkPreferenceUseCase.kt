package com.michaeltchuang.walletsdk.core.network.domain.usecase

import com.michaeltchuang.walletsdk.core.network.domain.NodePreferenceRepository
import com.michaeltchuang.walletsdk.core.network.model.AlgorandNetwork

class SaveNetworkPreferenceUseCase(
    private val nodePreferenceRepository: NodePreferenceRepository,
) {
    suspend operator fun invoke(network: AlgorandNetwork) {
        nodePreferenceRepository.saveNodePreference(network)
    }
}
