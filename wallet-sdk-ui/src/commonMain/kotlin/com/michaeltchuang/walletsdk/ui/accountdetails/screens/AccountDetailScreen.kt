package com.michaeltchuang.walletsdk.ui.accountdetails.screens

import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.account
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.address_copied_to_clipboard
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.copy_address
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.dispenser_add_funds_to_your_account
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_algo_sign
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_copy
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_key
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_send
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_unlink
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.remove_account
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.send_funds_to_another_account
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.view_passphrase
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.core.foundation.utils.Log
import com.michaeltchuang.walletsdk.core.foundation.utils.WalletSdkConstants
import com.michaeltchuang.walletsdk.ui.accountdetails.components.AccountDetailItem
import com.michaeltchuang.walletsdk.ui.accountdetails.components.AccountDetailWebviewItem
import com.michaeltchuang.walletsdk.ui.accountdetails.viewmodels.AccountDetailViewModel
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.ui.base.navigation.AlgoKitScreens
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "AccountStatusScreen"

@Composable
fun AccountDetailScreen(
    navController: NavController,
    address: String,
    showSnackBar: (String) -> Unit,
    onAccountDeleted: () -> Unit,
) {
    val viewModel: AccountDetailViewModel = koinViewModel()
    val viewState by viewModel.state.collectAsState()

    // Handle ViewEvents
    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect { event ->
            when (event) {
                is AccountDetailViewModel.ViewEvent.AccountDeleted -> {
                    Log.d(TAG, "Account deleted: ${event.message}")
                    onAccountDeleted()
                }

                is AccountDetailViewModel.ViewEvent.Error -> {
                    Log.e(TAG, "Error: ${event.message}")
                    showSnackBar(event.message)
                }
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
        Text(
            text = stringResource(Res.string.account),
            modifier =
                Modifier
                    .padding(vertical = 16.dp),
            color = AlgoKitTheme.colors.textMain,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
        )

        when (val state = viewState) {
            is AccountDetailViewModel.ViewState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = AlgoKitTheme.colors.positive,
                    )
                }
            }

            is AccountDetailViewModel.ViewState.Idle -> {
                // Initial state - could show empty state or loading
            }

            is AccountDetailViewModel.ViewState.Content -> {
                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    CopyAddress(address, showSnackBar)

                    Spacer(modifier = Modifier.height(16.dp))

                    AccountDetailItem(
                        icon = Res.drawable.ic_key,
                        title = stringResource(Res.string.view_passphrase),
                    ) {
                        navController.navigate(
                            AlgoKitScreens.PASS_PHRASE_ACKNOWLEDGE_SCREEN.name,
                        )
                    }

                    // Only show dispenser on TestNet
                    if (state.isTestNet) {
                        Spacer(modifier = Modifier.height(16.dp))

                        AccountDetailWebviewItem(
                            icon = Res.drawable.ic_algo_sign,
                            title = stringResource(Res.string.dispenser_add_funds_to_your_account),
                            url = "https://dispenser.testnet.aws.algodev.network/?account=$address",
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AccountDetailItem(
                        icon = Res.drawable.ic_send,
                        isRemoveAccount = false,
                        title = stringResource(Res.string.send_funds_to_another_account),
                    ) {
                        navController.navigate(
                            "${AlgoKitScreens.SEND_ALGO_SCREEN.name}?sender=$address",
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    AccountDetailItem(
                        icon = Res.drawable.ic_unlink,
                        isRemoveAccount = true,
                        title = stringResource(Res.string.remove_account),
                    ) {
                        viewModel.deleteAccount(address)
                    }
                }
            }
        }
    }
}

@Composable
fun CopyAddress(
    address: String,
    showSnackBar: (String) -> Unit,
) {
    val clipboardManager = LocalClipboardManager.current
    val copyMessage = stringResource(Res.string.address_copied_to_clipboard)

    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable {
                    clipboardManager.setText(AnnotatedString(address))
                    showSnackBar(copyMessage)
                },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_copy),
            contentDescription = stringResource(Res.string.copy_address),
            tint = AlgoKitTheme.colors.textMain,
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = stringResource(Res.string.copy_address),
                color = AlgoKitTheme.colors.textMain,
                style = AlgoKitTheme.typography.body.regular.sansMedium,
            )

            Text(
                text = address,
                color = AlgoKitTheme.colors.textGray,
                style = AlgoKitTheme.typography.body.regular.sansMedium,
                maxLines = 1,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    AlgoKitTheme {
        AccountDetailScreen(
            navController = rememberNavController(),
            address = WalletSdkConstants.SAMPLE_FALCON24_ADDRESS,
            showSnackBar = {},
            onAccountDeleted = {},
        )
    }
}
