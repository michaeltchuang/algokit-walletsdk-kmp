package com.michaeltchuang.walletsdk.ui.settings.screens

import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.cancel
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.mainnet_warning
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.mainnet_warning_desc
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.node_settings
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ok
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.core.network.domain.NodePreferenceRepository
import com.michaeltchuang.walletsdk.core.network.domain.provideNodePreferenceRepository
import com.michaeltchuang.walletsdk.core.network.model.AlgorandNetwork
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.AlgoKitTopBar
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

val networkNodeSettings = MutableStateFlow<AlgorandNetwork?>(null)

@Composable
fun NodeSettingsScreen(
    navController: NavController,
    nodeRepository: NodePreferenceRepository = provideNodePreferenceRepository(),
) {
    val coroutineScope = rememberCoroutineScope()
    val currentNetwork by nodeRepository
        .getSavedNodePreferenceFlow()
        .collectAsState(initial = null)
    var showMainnetWarningDialog by remember { mutableStateOf(false) }

    fun onNetworkSelected(network: AlgorandNetwork) {
        if (network != currentNetwork) {
            if (network == AlgorandNetwork.MAINNET) {
                showMainnetWarningDialog = true
            } else {
                coroutineScope.launch {
                    nodeRepository.saveNodePreference(network)
                    networkNodeSettings.value = network
                }
            }
        }
    }

    if (showMainnetWarningDialog) {
        AlertDialog(
            onDismissRequest = { showMainnetWarningDialog = false },
            title = {
                Text(
                    text = stringResource(Res.string.mainnet_warning),
                    color = AlgoKitTheme.colors.textMain,
                    style = AlgoKitTheme.typography.body.large.sansMedium,
                )
            },
            text = {
                Text(
                    text = stringResource(Res.string.mainnet_warning_desc),
                    color = AlgoKitTheme.colors.textMain,
                    style = AlgoKitTheme.typography.body.regular.sansMedium,
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showMainnetWarningDialog = false
                        coroutineScope.launch {
                            nodeRepository.saveNodePreference(AlgorandNetwork.MAINNET)
                            networkNodeSettings.value = AlgorandNetwork.MAINNET
                        }
                    },
                ) {
                    Text(
                        text = stringResource(Res.string.ok),
                        color = AlgoKitTheme.colors.positive,
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showMainnetWarningDialog = false },
                ) {
                    Text(
                        text = stringResource(Res.string.cancel),
                        color = AlgoKitTheme.colors.textMain,
                    )
                }
            },
            containerColor = AlgoKitTheme.colors.background,
            titleContentColor = AlgoKitTheme.colors.textMain,
            textContentColor = AlgoKitTheme.colors.textMain,
        )
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = AlgoKitTheme.colors.background)
                .padding(horizontal = 16.dp),
    ) {
        AlgoKitTopBar(
            title = stringResource(Res.string.node_settings),
        ) {
            navController.popBackStack()
        }

        Spacer(modifier = Modifier.height(8.dp))

        AlgorandNetwork.entries.forEach { network ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .clickable { onNetworkSelected(network) }
                        .padding(vertical = 16.dp),
            ) {
                Text(
                    text = network.displayName,
                    color = AlgoKitTheme.colors.textMain,
                    modifier = Modifier.weight(1f),
                    style = AlgoKitTheme.typography.body.regular.sansMedium,
                )
                RadioButton(
                    selected = network == currentNetwork,
                    onClick = { onNetworkSelected(network) },
                    colors =
                        RadioButtonDefaults.colors(
                            selectedColor = AlgoKitTheme.colors.positive,
                            unselectedColor = Color.LightGray,
                        ),
                )
            }
        }
    }
}

@Preview
@Composable
fun NodeSettingsScreenPreview() {
    AlgoKitTheme {
        NodeSettingsScreen(navController = rememberNavController())
    }
}
