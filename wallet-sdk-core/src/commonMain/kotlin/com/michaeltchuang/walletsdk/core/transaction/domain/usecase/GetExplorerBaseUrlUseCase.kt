package com.michaeltchuang.walletsdk.core.transaction.domain.usecase

import com.michaeltchuang.walletsdk.core.network.utils.getExplorerBaseUrl

class GetExplorerBaseUrlUseCase {
    suspend operator fun invoke(): String = getExplorerBaseUrl()
}
