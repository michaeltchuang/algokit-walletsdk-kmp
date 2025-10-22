package com.michaeltchuang.walletsdk.demo.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.michaeltchuang.walletsdk.core.network.domain.usecase.GetCurrentNetworkUseCase
import com.michaeltchuang.walletsdk.ui.settings.screens.networkNodeSettings
import kotlinx.coroutines.launch

class AppViewModel(
    private val getCurrentNetworkUseCase: GetCurrentNetworkUseCase,
) : ViewModel() {
    init {
        initializeNetworkStatus()
    }

    private fun initializeNetworkStatus() {
        viewModelScope.launch {
            getCurrentNetworkUseCase().collect { network ->
                networkNodeSettings.value = network
            }
        }
    }
}
