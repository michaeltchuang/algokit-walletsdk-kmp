package co.algorand.app.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.algorand.app.ui.screens.CoreActionsBottomSheet
import co.algorand.app.ui.screens.PeraTypographyPreviewScreen
import co.algorand.app.ui.screens.PeraTypographyPreviewScreenNavigation
import co.algorand.app.ui.screens.QrScannerScreen
import co.algorand.app.ui.screens.QrScannerScreenNavigation
import co.algorand.app.utils.Constants
import com.michaeltchuang.walletsdk.ui.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.webview.AlgoKitWebViewPlatformScreen
import com.michaeltchuang.walletsdk.webview.WebViewPlatformScreenNavigation
import kotlinx.coroutines.launch


@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    val isBottomSheetVisible = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier
            .background(color = AlgoKitTheme.colors.background)
            .fillMaxSize(),
        topBar = {
            TopBar()
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier)
        },
        bottomBar = {
            AlgoKitNavigationBar(navController) {
                isBottomSheetVisible.value = true
            }
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Accounts,
            modifier = Modifier.padding(paddingValues = paddingValues),
        ) {
            getBottomNavigationGraph(navController, snackbarHostState)
            composable<PeraTypographyPreviewScreenNavigation> {
                PeraTypographyPreviewScreen()
            }
            composable<QrScannerScreenNavigation> {
                QrScannerScreen {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = it,
                            duration = SnackbarDuration.Short)
                    }
                }
            }
            composable<WebViewPlatformScreenNavigation> {
                AlgoKitWebViewPlatformScreen(Constants.REPO_URL)
            }
            composable<WebViewPlatformScreenNavigation> {
                AlgoKitWebViewPlatformScreen(Constants.REPO_URL)
            }
        }
        CoreActionsBottomSheet(paddingValues, isBottomSheetVisible)
    }
}
