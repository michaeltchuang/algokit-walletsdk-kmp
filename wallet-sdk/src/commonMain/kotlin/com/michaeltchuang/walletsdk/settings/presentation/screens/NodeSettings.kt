package com.michaeltchuang.walletsdk.settings.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.node_settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.settings.domain.NodePreferenceRepository
import com.michaeltchuang.walletsdk.settings.domain.provideNodePreferenceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

enum class AlgorandNetwork(
    val displayName: String,
) { MAINNET("Algorand MainNet Node"),
    TESTNET("TestNet"),
}

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

    fun onNetworkSelected(network: AlgorandNetwork) {
        if (network != currentNetwork) {
            coroutineScope.launch {
                nodeRepository.saveNodePreference(network)
                networkNodeSettings.value = currentNetwork
            }
        }
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
