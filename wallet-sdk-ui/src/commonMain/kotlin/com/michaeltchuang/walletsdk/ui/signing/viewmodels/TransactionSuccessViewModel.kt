package com.michaeltchuang.walletsdk.ui.signing.viewmodels

import androidx.lifecycle.ViewModel
import com.michaeltchuang.walletsdk.transaction.domain.usecase.GetExplorerBaseUrlUseCase

class TransactionSuccessViewModel(
    val getExplorerBaseUrlUseCase: GetExplorerBaseUrlUseCase,
) : ViewModel() {
    suspend fun getExplorerBaseUrl(): String = getExplorerBaseUrlUseCase()
}
