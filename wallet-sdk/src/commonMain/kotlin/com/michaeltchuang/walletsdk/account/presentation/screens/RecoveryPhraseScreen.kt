package com.michaeltchuang.walletsdk.account.presentation.screens

import algokit_walletsdk_kmp.wallet_sdk.generated.resources.Res
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.enter_your_recovery_passphrase
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.finish_account_creation
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_clipboard
import algokit_walletsdk_kmp.wallet_sdk.generated.resources.ic_info
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.final_class.webview_multiplatform_mobile.webview.WebViewPlatform
import com.final_class.webview_multiplatform_mobile.webview.controller.rememberWebViewController
import com.michaeltchuang.walletsdk.account.domain.model.core.OnboardingAccountType
import com.michaeltchuang.walletsdk.account.domain.model.local.AccountMnemonic
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitScreens
import com.michaeltchuang.walletsdk.account.presentation.viewmodels.RecoverPassphraseViewModel
import com.michaeltchuang.walletsdk.foundation.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.foundation.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.foundation.designsystem.widget.button.AlgoKitPrimaryButton
import com.michaeltchuang.walletsdk.foundation.utils.WalletSdkConstants
import com.michaeltchuang.walletsdk.foundation.utils.WalletSdkConstants.SAMPLE_ALGO25_MNEMONIC
import com.michaeltchuang.walletsdk.foundation.utils.splitMnemonic
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoveryPhraseScreen(
    navController: NavController,
    accountType: AccountMnemonic.AccountType,
    mnemonicString: String,
    snackBar: (message: String) -> Unit,
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    val viewModel: RecoverPassphraseViewModel = koinViewModel()
    var mnemonic by rememberSaveable { mutableStateOf(mnemonicString) }
    var mnemonicList by rememberSaveable { mutableStateOf(mnemonicString.splitMnemonic()) }
    val webViewController by rememberWebViewController()
    WebViewPlatform(webViewController = webViewController)

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is RecoverPassphraseViewModel.ViewEvent.NavigateToAccountNameScreen -> {
                    navController.navigate(AlgoKitScreens.CREATE_ACCOUNT_NAME.name)
                }

                is RecoverPassphraseViewModel.ViewEvent.ShowError -> {
                    snackBar(it.error)
                }
            }
        }
    }

    Box(
        modifier =
            Modifier
                .background(color = AlgoKitTheme.colors.background)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(horizontal = 16.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            ) {
                AlgoKitTopBar(
                    modifier =
                        Modifier
                            .wrapContentSize(),
                    onClick = { navController.popBackStack() },
                )
                Row(
                    modifier =
                        Modifier
                            .wrapContentSize()
                            .padding(end = 16.dp),
                ) {
                    IconButton(
                        onClick = {
                            clipboardManager.getText()?.text?.let {
                                viewModel.onClipBoardPastedMnemonic(it) {
                                    mnemonicList = it.splitMnemonic()
                                    mnemonic = it
                                }
                            }
                        },
                        modifier =
                            Modifier
                                .size(32.dp)
                                .padding(end = 8.dp),
                    ) {
                        Icon(
                            tint = AlgoKitTheme.colors.textMain,
                            painter = painterResource(Res.drawable.ic_clipboard),
                            contentDescription = "Back",
                        )
                    }

                    IconButton(
                        onClick = {
                            webViewController.open(WalletSdkConstants.RECOVER_ACCOUNT_LEARN_MORE)
                        },
                        modifier =
                            Modifier
                                .size(32.dp)
                                .padding(start = 8.dp),
                    ) {
                        Icon(
                            tint = AlgoKitTheme.colors.textMain,
                            painter = painterResource(Res.drawable.ic_info),
                            contentDescription = "Back",
                        )
                    }
                }
            }

            RecoveryPhraseContent(
                modifier = Modifier.fillMaxHeight(0.8f),
                predefinedWords = mnemonicList,
            )
            Spacer(modifier = Modifier.height(32.dp))
            AlgoKitPrimaryButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                {
                    when (mnemonicList.size) {
                        OnboardingAccountType.Algo25.wordCount -> {
                            viewModel.onRecoverAccount(
                                mnemonic,
                                OnboardingAccountType.Algo25,
                            )
                        }

                        OnboardingAccountType.Falcon24.wordCount -> {
                            viewModel.onRecoverAccount(
                                mnemonic,
                                OnboardingAccountType.Falcon24,
                            )
                        }

                        else -> { }
                    }
                },
                text = stringResource(Res.string.finish_account_creation),
            )
        }
    }
}

@Composable
fun RecoveryPhraseContent(
    modifier: Modifier,
    predefinedWords: List<String>,
) {
    Column(modifier = modifier) {
        Text(
            style = AlgoKitTheme.typography.title.regular.sansMedium,
            text = stringResource(Res.string.enter_your_recovery_passphrase),
            color = AlgoKitTheme.colors.textMain,
        )
        Spacer(modifier = Modifier.height(32.dp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize(),
        ) {
            val wordCount = if (predefinedWords.isNotEmpty()) predefinedWords.size else 25
            items((wordCount + 1) / 2) { rowIndex ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    val leftIndex = rowIndex
                    val rightIndex = rowIndex + (wordCount + 1) / 2

                    if (leftIndex < wordCount) {
                        RecoveryWordField(
                            index = leftIndex,
                            value = if (predefinedWords.isNotEmpty()) predefinedWords[leftIndex] else "",
                            onValueChange = { /*recoveryWords[leftIndex] = it*/ },
                            modifier = Modifier.weight(1f),
                        )
                    }

                    if (rightIndex < wordCount) {
                        RecoveryWordField(
                            index = rightIndex,
                            value = if (predefinedWords.isNotEmpty()) predefinedWords[rightIndex] else "",
                            onValueChange = { /* recoveryWords[rightIndex] = it*/ },
                            modifier = Modifier.weight(1f),
                        )
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun RecoveryWordField(
    index: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(8.dp),
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            text = "${index + 1}",
            style = AlgoKitTheme.typography.title.regular.sans,
            fontSize = 14.sp,
            color = AlgoKitTheme.colors.textMain,
        )

        Column {
            Text(
                text = value,
                fontSize = 16.sp,
                color = AlgoKitTheme.colors.textMain,
                style = AlgoKitTheme.typography.title.regular.sans,
            )
            Spacer(modifier = Modifier.height(4.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = Color.Gray,
            )
        }
    }
}

@Preview
@Composable
fun RecoveryPhraseScreenPreview() {
    val words = SAMPLE_ALGO25_MNEMONIC
    RecoveryPhraseScreen(
        rememberNavController(),
        accountType = AccountMnemonic.AccountType.Falcon24,
        words,
    ) {}
}
