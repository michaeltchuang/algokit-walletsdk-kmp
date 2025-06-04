package co.algorand.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.ui.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.webview.AlgoKitWebViewScreen

@Composable
fun DiscoverScreen(
    navController: NavController,
    snackbarViewModel: SnackbarViewModel,
    tag: String,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize().background(AlgoKitTheme.colors.background)
    ) {
        AlgoKitWebViewScreen(
            modifier = Modifier.fillMaxSize(),
            url = "https://github.com/michaeltchuang/algokit-walletsdk-kmp"
        )
    }
}
