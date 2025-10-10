package com.michaeltchuang.walletsdk.account.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.already_have_an_account
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.create_a_new_wallet
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.hd_wallet
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_hd_wallet
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_key
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_right_arrow
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.import_an_account
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.new_to_algorand
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.pera_icon_3d
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.right_arrow
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.welcome_to_pera
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitScreens
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.OnboardingIntroViewModel
import com.michaeltchuang.walletsdk.foundation.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.foundation.designsystem.theme.AlgoKitTheme.typography
import com.michaeltchuang.walletsdk.foundation.designsystem.widget.button.AlgoKitTertiaryButton
import com.michaeltchuang.walletsdk.foundation.designsystem.widget.icon.AlgoKitIcon
import com.michaeltchuang.walletsdk.foundation.utils.Log
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

private const val TAG = "OnboardingIntroScreen"

@Composable
fun OnboardingIntroScreen(navController: NavController = rememberNavController()) {
    val viewModel: OnboardingIntroViewModel = koinViewModel()

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is OnboardingIntroViewModel.ViewEvent.AccountCreated -> {
                    navController.navigate(AlgoKitScreens.CREATE_ACCOUNT_NAME.name)
                }

                is OnboardingIntroViewModel.ViewEvent.Error -> {
                    Log.d(TAG, it.message)
                }
            }
        }
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .background(color = AlgoKitTheme.colors.background)
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp),
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(top = 50.dp)
                        .fillMaxWidth(),
                style = typography.title.regular.sansMedium,
                color = AlgoKitTheme.colors.textMain,
                text =
                    stringResource(
                        Res.string.welcome_to_pera,
                    ),
            )
            Box(
                modifier =
                    Modifier
                        .weight(1f, fill = true),
                contentAlignment = Alignment.TopEnd,
            ) {
                AlgoKitIcon(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(Res.drawable.pera_icon_3d),
                    contentDescription = stringResource(Res.string.welcome_to_pera),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        CreateNewWalletWidget(viewModel)

        Spacer(modifier = Modifier.height(40.dp))

        ImportAccountWidget(navController)

        Spacer(modifier = Modifier.weight(1f))

        TermsAndPrivacy()
    }
}

@Composable
private fun CreateNewWalletWidget(viewModel: OnboardingIntroViewModel) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            style = typography.body.regular.sans,
            text = stringResource(Res.string.new_to_algorand),
            color = AlgoKitTheme.colors.textGray,
        )

        Spacer(Modifier.height(12.dp))

        AlgoKitTertiaryButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            onClick = {
                viewModel.createFalcon24Account()
            },
            text = stringResource(Res.string.create_a_new_wallet),
            leftIcon = {
                AlgoKitIcon(
                    painter = painterResource(Res.drawable.ic_hd_wallet),
                    contentDescription = stringResource(Res.string.hd_wallet),
                )
            },
            rightIcon = {
                AlgoKitIcon(
                    painter = painterResource(Res.drawable.ic_right_arrow),
                    contentDescription = stringResource(Res.string.right_arrow),
                    tintColor = AlgoKitTheme.colors.textGray,
                )
            },
        )
    }
}

@Composable
private fun ImportAccountWidget(navController: NavController) {
    Column {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            style = typography.body.regular.sans,
            text = stringResource(Res.string.already_have_an_account),
            color = AlgoKitTheme.colors.textGray,
        )

        Spacer(Modifier.height(12.dp))

        AlgoKitTertiaryButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            onClick = {
                navController.navigate(AlgoKitScreens.ACCOUNT_RECOVERY_TYPE_SCREEN.name)
            },
            text = stringResource(Res.string.import_an_account),
            leftIcon = {
                AlgoKitIcon(
                    painter = painterResource(Res.drawable.ic_key),
                    contentDescription = stringResource(Res.string.hd_wallet),
                )
            },
            rightIcon = {
                AlgoKitIcon(
                    painter = painterResource(Res.drawable.ic_right_arrow),
                    contentDescription = stringResource(Res.string.right_arrow),
                    tintColor = AlgoKitTheme.colors.textGray,
                )
            },
        )
    }
}

@Preview
@Composable
private fun InitialRegisterIntroScreenPreview() {
    AlgoKitTheme {
        Column {
            OnboardingIntroScreen()
        }
    }
}
