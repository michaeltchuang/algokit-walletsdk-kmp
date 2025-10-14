package com.michaeltchuang.walletsdk.accountdetail.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.account
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.address_copied_to_clipboard
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.copy_address
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_copy
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_key
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_unlink
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.next
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.remove_account
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.view_passphrase
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.final_class.webview_multiplatform_mobile.webview.WebViewPlatform
import com.final_class.webview_multiplatform_mobile.webview.controller.rememberWebViewController
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitScreens
import com.michaeltchuang.walletsdk.accountdetail.presentation.viewmodels.AccountDetailViewModel
import com.michaeltchuang.walletsdk.foundation.designsystem.theme.AlgoKitTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AccountStatusScreen(
    navController: NavController,
    address: String,
    showSnackBar: (String) -> Unit,
    onAccountDeleted: () -> Unit,
) {
    val viewModel: AccountDetailViewModel = koinViewModel()
    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is AccountDetailViewModel.AccountsEvent.AccountDeleted -> {
                    onAccountDeleted()
                }

                is AccountDetailViewModel.AccountsEvent.ShowError -> {}
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = AlgoKitTheme.colors.background)
                .verticalScroll(rememberScrollState())
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

        Spacer(modifier = Modifier.height(16.dp))

        CopyAddress(address, showSnackBar)

        Spacer(modifier = Modifier.height(16.dp))

        AccountStatusItem(
            icon = Res.drawable.ic_key,
            title = stringResource(Res.string.view_passphrase),
        ) {
            navController.navigate(
                AlgoKitScreens.PASS_PHRASE_ACKNOWLEDGE_SCREEN.name,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        AccountStatusItem(
            icon = Res.drawable.ic_unlink,
            isRemoveAccount = true,
            title = stringResource(Res.string.remove_account),
        ) {
            viewModel.deleteAccount(address)
        }
    }
}


@Composable
fun CopyAddress(address: String, showSnackBar: (String) -> Unit) {
    val clipboardManager = LocalClipboardManager.current
    val copyMessage = stringResource(Res.string.address_copied_to_clipboard)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                clipboardManager.setText(AnnotatedString(address))
                showSnackBar(copyMessage)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_copy),
            contentDescription = stringResource(Res.string.copy_address),
            tint = AlgoKitTheme.colors.textMain,
            modifier = Modifier.size(24.dp)
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
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Composable
fun AccountStatusItem(
    icon: DrawableResource,
    isRemoveAccount: Boolean = false,
    title: String,
    onClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(vertical = 12.dp),
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = title,
            tint = if (isRemoveAccount) AlgoKitTheme.colors.negative else AlgoKitTheme.colors.textMain,
            modifier = Modifier.size(24.dp),
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = if (isRemoveAccount) AlgoKitTheme.colors.negative else AlgoKitTheme.colors.textMain,
            modifier = Modifier.weight(1f),
            style = AlgoKitTheme.typography.body.regular.sansMedium,
        )
        Icon(
            Icons.Default.KeyboardArrowRight,
            tint = AlgoKitTheme.colors.textMain,
            contentDescription = stringResource(Res.string.next),
        )
    }
}

@Preview
@Composable
fun SettingsScreenPreview() {
    AlgoKitTheme {
        AccountStatusScreen(
            navController = rememberNavController(),
            address = "ASDFGHJKL",
            showSnackBar = {},
            onAccountDeleted = {},
        )
    }
}
