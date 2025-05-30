package co.algorand.app.ui.screens

import algokit_walletsdk_kmp.composetestapp.generated.resources.Res
import algokit_walletsdk_kmp.composetestapp.generated.resources.nav_discover
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.ui.theme.PeraTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun DiscoverScreen(
    navController: NavController,
    snackbarViewModel: SnackbarViewModel,
    tag: String,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxSize().background(PeraTheme.colors.background)
    ) {
        Text(text = stringResource(Res.string.nav_discover), color = PeraTheme.colors.textMain)
    }
}