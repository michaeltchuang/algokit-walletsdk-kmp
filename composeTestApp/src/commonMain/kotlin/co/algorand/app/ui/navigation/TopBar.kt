package co.algorand.app.ui.navigation

import algokit_walletsdk_kmp.composetestapp.generated.resources.Res
import algokit_walletsdk_kmp.composetestapp.generated.resources.app_bar_header
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.michaeltchuang.walletsdk.ui.theme.AlgoKitTheme
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    TopAppBar(
        colors =
        TopAppBarDefaults.topAppBarColors(
            containerColor = AlgoKitTheme.colors.background,
            titleContentColor = AlgoKitTheme.colors.textMain,
        ),
        title = {
            Text(
                stringResource(resource = Res.string.app_bar_header),
                maxLines = 1,
            )
        },
    )
}
