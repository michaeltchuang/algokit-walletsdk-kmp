package com.michaeltchuang.walletsdk.demo

import androidx.compose.runtime.Composable
import com.michaeltchuang.walletsdk.demo.di.initKoinConfig
import com.michaeltchuang.walletsdk.demo.ui.navigation.AppNavigation
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import org.koin.compose.KoinApplication

@Composable
internal fun App() {
    KoinApplication(
        application = initKoinConfig,
    ) {
        AlgoKitTheme {
            AppNavigation()
        }
    }
}

internal expect fun openUrl(url: String?)
