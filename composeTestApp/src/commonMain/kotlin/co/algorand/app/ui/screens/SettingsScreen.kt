package co.algorand.app.ui.screens

import algokit_walletsdk_kmp.composetestapp.generated.resources.Res
import algokit_walletsdk_kmp.composetestapp.generated.resources.ic_dark_mode
import algokit_walletsdk_kmp.composetestapp.generated.resources.ic_light_mode
import algokit_walletsdk_kmp.composetestapp.generated.resources.nav_settings
import algokit_walletsdk_kmp.composetestapp.generated.resources.open_github
import algokit_walletsdk_kmp.composetestapp.generated.resources.theme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.designsystem.theme.LocalThemeIsDark
import com.michaeltchuang.walletsdk.webview.WebViewPlatformScreenNavigation
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun SettingsScreen(
    navController: NavController,
    snackbarViewModel: SnackbarViewModel,
    tag: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize().background(AlgoKitTheme.colors.background),
    ) {
        Text(text = stringResource(Res.string.nav_settings), color = AlgoKitTheme.colors.textMain)

        var isDark by LocalThemeIsDark.current
        val icon =
            remember(isDark) {
                if (isDark) {
                    Res.drawable.ic_light_mode
                } else {
                    Res.drawable.ic_dark_mode
                }
            }

        ElevatedButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { isDark = !isDark },
            content = {
                Icon(vectorResource(icon), contentDescription = null)
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(stringResource(Res.string.theme))
            },
        )

     /*   ElevatedButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { navController.navigate(AlgoKitTypographyPreviewScreenNavigation) },
            content = {
                Text("Typography")
            },
        )*/

   /*     ElevatedButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { navController.navigate(QrScannerScreenNavigation) },
            content = {
                Text("QR scanner")
            },
        )*/

        TextButton(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp).widthIn(min = 200.dp),
            onClick = { navController.navigate(WebViewPlatformScreenNavigation) },
        ) {
            Text(stringResource(Res.string.open_github))
        }
    }
}
