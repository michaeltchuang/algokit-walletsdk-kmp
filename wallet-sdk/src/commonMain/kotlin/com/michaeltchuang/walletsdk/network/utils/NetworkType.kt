package com.michaeltchuang.walletsdk.network.utils

import com.michaeltchuang.walletsdk.settings.domain.provideNodePreferenceRepository
import com.michaeltchuang.walletsdk.settings.presentation.screens.AlgorandNetwork
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants.INDEXER_MAINNET_URL
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants.INDEXER_TESTNET_URL
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants.NODE_MAINNET_URL
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants.NODE_TESTNET_URL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

suspend fun getIndexerBaseUrl(): String {
    return CoroutineScope(Dispatchers.IO).async {
        val networkType = provideNodePreferenceRepository().getSavedNodePreferenceFlow().first()
        when (networkType) {
            AlgorandNetwork.MAINNET -> INDEXER_MAINNET_URL
            AlgorandNetwork.TESTNET -> INDEXER_TESTNET_URL
        }
    }.await()
}

suspend fun getNodeBaseUrl(): String {
    return CoroutineScope(Dispatchers.IO).async {
        val networkType = provideNodePreferenceRepository().getSavedNodePreferenceFlow().first()
        when (networkType) {
            AlgorandNetwork.MAINNET -> NODE_MAINNET_URL
            AlgorandNetwork.TESTNET -> NODE_TESTNET_URL
        }
    }.await()
}
