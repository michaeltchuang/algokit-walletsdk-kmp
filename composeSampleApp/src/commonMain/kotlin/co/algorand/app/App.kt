package co.algorand.app

import androidx.compose.runtime.Composable
import co.algorand.app.di.initKoinConfig
import co.algorand.app.ui.navigation.AppNavigation
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
