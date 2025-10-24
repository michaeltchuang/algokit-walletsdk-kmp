package com.michaeltchuang.walletsdk.ui.signing.screens

import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_hd_wallet
import algokit_walletsdk_kmp.wallet_sdk_ui.generated.resources.ic_wallet
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.michaeltchuang.walletsdk.core.account.domain.model.core.AccountRegistrationType
import com.michaeltchuang.walletsdk.core.account.domain.model.custom.AccountLite
import com.michaeltchuang.walletsdk.core.foundation.utils.formatAmount
import com.michaeltchuang.walletsdk.core.foundation.utils.toShortenedAddress
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme.typography
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.icon.AlgoKitIconRoundShape
import com.michaeltchuang.walletsdk.ui.base.navigation.AlgoKitScreens
import com.michaeltchuang.walletsdk.ui.signing.viewmodels.SelectAccountViewModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SelectAccountScreen(
    navController: NavController,
    receiverAddress: String,
    amount: String,
) {
    val viewModel: SelectAccountViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val events = viewModel.viewEvent.collectAsStateWithLifecycle(initialValue = null)

    LaunchedEffect(receiverAddress, amount) {
        viewModel.setup(receiverAddress, amount)
    }

    LaunchedEffect(events.value) {
        events.value?.let { event ->
            when (event) {
                is SelectAccountViewModel.ViewEvent.NavigateToAssetTransfer -> {
                    navController.navigate(
                        AlgoKitScreens.ASSET_TRANSFER_SCREEN.name +
                            "?sender=${event.senderAddress}" +
                            "&receiver=${event.receiverAddress}" +
                            "&amount=${event.amount}",
                    ) {
                        popUpTo(AlgoKitScreens.QR_CODE_SCANNER_SCREEN.name) {
                            inclusive = true
                        }
                    }
                }

                is SelectAccountViewModel.ViewEvent.ShowError -> {
                    // Error is already shown in the UI
                }
            }
        }
    }

    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AlgoKitTopBar(
                title = "Select Account",
                onClick = { navController.popBackStack() },
            )

            Spacer(modifier = Modifier.height(16.dp))

            when (state) {
                is SelectAccountViewModel.AccountsState.Loading -> {
                    CenteredLoader()
                }

                is SelectAccountViewModel.AccountsState.Content -> {
                    val accounts = (state as SelectAccountViewModel.AccountsState.Content).accounts
                    if (accounts.isEmpty()) {
                        CenteredMessage("No accounts available")
                    } else {
                        AccountsList(
                            accounts = accounts,
                            onAccountItemClick = { address ->
                                viewModel.onAccountSelected(address)
                            },
                        )
                    }
                }

                is SelectAccountViewModel.AccountsState.Error -> {
                    CenteredMessage(
                        text = "Error: ${(state as SelectAccountViewModel.AccountsState.Error).message}",
                        color = Color.Red,
                    )
                }
            }
        }
    }
}

@Composable
private fun CenteredLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun CenteredMessage(
    text: String,
    color: Color = Color.Unspecified,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = color,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun AccountsList(
    accounts: List<AccountLite>,
    onAccountItemClick: (String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(accounts) { account ->
            AccountItem(
                account = account,
                onAccountItemClick = onAccountItemClick,
            )
        }
    }
}

@Composable
private fun AccountItem(
    account: AccountLite,
    onAccountItemClick: (address: String) -> Unit,
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    onAccountItemClick(account.address)
                }),
        elevation = CardDefaults.cardElevation(4.dp),
        colors =
            CardDefaults.cardColors(
                containerColor = AlgoKitTheme.colors.layerGray,
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AlgoKitIconRoundShape(
                imageVector = vectorResource(getWalletIcon(account.registrationType)),
                contentDescription = "Wallet Icon",
                backgroundColor = AlgoKitTheme.colors.wallet4,
            )

            Column(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
            ) {
                Text(
                    text = account.customName.ifEmpty { account.address.toShortenedAddress() },
                    style = typography.body.large.sansMedium,
                    color = AlgoKitTheme.colors.textMain,
                )
                Text(
                    text = getAccountTypeText(account.registrationType),
                    style = typography.footnote.mono,
                    color = AlgoKitTheme.colors.textGray,
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = "\u00A6${account.balance?.formatAmount() ?: "0.00"}",
                    fontSize = 16.sp,
                    style = typography.footnote.sansMedium,
                    color = AlgoKitTheme.colors.textMain,
                )
            }
        }
    }
}

private fun getWalletIcon(registrationType: AccountRegistrationType): DrawableResource =
    when (registrationType) {
        is AccountRegistrationType.HdKey -> Res.drawable.ic_hd_wallet
        else -> Res.drawable.ic_wallet
    }

private fun getAccountTypeText(registrationType: AccountRegistrationType): String =
    when (registrationType) {
        is AccountRegistrationType.HdKey -> "HD Account"
        is AccountRegistrationType.Algo25 -> "Algo25"
        is AccountRegistrationType.Falcon24 -> "Falcon24"
        is AccountRegistrationType.NoAuth -> "Watch"
        is AccountRegistrationType.LedgerBle -> "Ledger"
    }
