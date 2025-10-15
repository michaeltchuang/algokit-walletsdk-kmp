package com.michaeltchuang.walletsdk.ui.signing.viewmodels

import androidx.lifecycle.ViewModel
import com.michaeltchuang.walletsdk.core.transaction.domain.usecase.GetExplorerBaseUrlUseCase

class TransactionSuccessViewModel(
    val getExplorerBaseUrlUseCase: GetExplorerBaseUrlUseCase,
) : ViewModel() {
    suspend fun getExplorerBaseUrl(): String = getExplorerBaseUrlUseCase()
}
