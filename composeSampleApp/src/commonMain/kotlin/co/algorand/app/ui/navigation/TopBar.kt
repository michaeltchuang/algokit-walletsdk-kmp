package co.algorand.app.ui.navigation

import algokit_walletsdk_kmp.composesampleapp.generated.resources.Res
import algokit_walletsdk_kmp.composesampleapp.generated.resources.app_name
import algokit_walletsdk_kmp.composesampleapp.generated.resources.ic_qr_scan
import algokit_walletsdk_kmp.composesampleapp.generated.resources.ic_settings
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val scop = rememberCoroutineScope()
    TopAppBar(
        colors =
            TopAppBarDefaults.topAppBarColors(
                containerColor = AlgoKitTheme.colors.background,
                titleContentColor = AlgoKitTheme.colors.textMain,
            ),
        title = {
            Text(
                stringResource(Res.string.app_name),
                maxLines = 1,
            )
        },
        actions = {
            Row {
                Icon(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .padding(horizontal = 8.dp)
                            .clickable(onClick = {
                                scop.launch {
                                    ACTIONS.qrClickEvent.emit(true)
                                }
                            }),
                    painter = painterResource(Res.drawable.ic_qr_scan),
                    contentDescription = "qr",
                    tint = AlgoKitTheme.colors.textMain,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier =
                        Modifier
                            .size(44.dp)
                            .padding(horizontal = 8.dp)
                            .clickable(onClick = {
                                scop.launch {
                                    ACTIONS.settingsClickEvent.emit(true)
                                }
                            }),
                    painter = painterResource(Res.drawable.ic_settings),
                    contentDescription = "qr",
                    tint = AlgoKitTheme.colors.textMain,
                )
            }
        },
    )
}

object ACTIONS {
    val qrClickEvent = MutableSharedFlow<Boolean>()
    val settingsClickEvent = MutableSharedFlow<Boolean>()
}

@Preview
@Composable
fun TopBarPreview() {
    AlgoKitTheme {
    }
}
