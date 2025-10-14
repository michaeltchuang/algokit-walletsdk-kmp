package com.michaeltchuang.walletsdk.transaction.domain.usecase

import com.michaeltchuang.walletsdk.network.utils.getExplorerBaseUrl

class GetExplorerBaseUrlUseCase {
    suspend operator fun invoke(): String = getExplorerBaseUrl()
}
