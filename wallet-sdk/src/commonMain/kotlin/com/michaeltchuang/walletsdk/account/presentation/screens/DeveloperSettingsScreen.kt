package com.michaeltchuang.walletsdk.account.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.create_legacy_algo25_account
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.developer_settings
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_node
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_wallet
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.node_settings
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitScreens
import com.michaeltchuang.walletsdk.account.presentation.components.SettingsItem
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.OnboardingAccountTypeViewModel
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.utils.Log
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "CreateAccountTypeScreen"

@Composable
fun DeveloperSettingsScreen(navController: NavController, onClick: (message: String) -> Unit) {
    val viewModel: OnboardingAccountTypeViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is OnboardingAccountTypeViewModel.ViewEvent.AccountCreated -> {
                    navController.navigate(AlgoKitScreens.CREATE_ACCOUNT_NAME.name)
                    Log.d(TAG, it.accountCreation.address)
                }

                is OnboardingAccountTypeViewModel.ViewEvent.Error -> {
                    Log.d(TAG, it.message)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = AlgoKitTheme.colors.background)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        AlgoKitTopBar(
            title = stringResource(Res.string.developer_settings),
            onClick = { navController.popBackStack() })

        SettingsItem(
            Res.drawable.ic_node,
            stringResource(Res.string.node_settings)
        ) {
            onClick(WalletSdkConstants.FEATURE_NOT_SUPPORTED_YET)
        }

        SettingsItem(
            Res.drawable.ic_wallet,
            stringResource(Res.string.create_legacy_algo25_account)
        ) {
            viewModel.createAlgo25Account()
        }
    }

}

@Preview
@Composable
fun DeveloperSettingsScreenPreview() {
    AlgoKitTheme {
        DeveloperSettingsScreen(navController = rememberNavController()) {

        }
    }
}
