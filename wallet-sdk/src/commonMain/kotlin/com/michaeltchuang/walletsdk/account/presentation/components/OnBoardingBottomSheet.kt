package com.michaeltchuang.walletsdk.account.presentation.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.michaeltchuang.walletsdk.account.presentation.screens.AccountRecoveryTypeSelectionScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.AccountStatusScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.CreateAccountNameScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.DeveloperSettingsScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.HdWalletSelectionScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.InitialRegisterIntroScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.OnboardingAccountTypeScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.PassphraseAcknowledgeScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.RecoverAnAccountScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.RecoveryPhraseScreen
import com.michaeltchuang.walletsdk.account.presentation.screens.ViewPassphraseScreen
import com.michaeltchuang.walletsdk.deeplink.model.DeepLink
import com.michaeltchuang.walletsdk.deeplink.model.KeyRegTransactionDetail
import com.michaeltchuang.walletsdk.deeplink.presentation.screens.QRCodeScannerScreen
import com.michaeltchuang.walletsdk.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.settings.presentation.screens.NodeSettingsScreen
import com.michaeltchuang.walletsdk.settings.presentation.screens.SettingsScreen
import com.michaeltchuang.walletsdk.settings.presentation.screens.ThemeScreen
import com.michaeltchuang.walletsdk.transaction.presentation.screens.TransactionSignatureRequestScreen
import com.michaeltchuang.walletsdk.transaction.presentation.screens.TransactionSuccessScreen
import com.michaeltchuang.walletsdk.utils.WalletSdkConstants.REPO_URL
import com.michaeltchuang.walletsdk.utils.getData
import com.michaeltchuang.walletsdk.webview.AlgoKitWebViewPlatformScreen
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

enum class AlgoKitEvent {
    ALGO25_ACCOUNT_CREATED,
    ClOSE_BOTTOMSHEET,
    HD_ACCOUNT_CREATED,
}

enum class AlgoKitScreens {
    ACCOUNT_RECOVERY_TYPE_SCREEN,
    CREATE_ACCOUNT_NAME,
    ON_BOARDING_ACCOUNT_TYPE_SCREEN,
    HD_WALLET_SELECTION_SCREEN,
    INITIAL_REGISTER_INTRO_SCREEN,
    QR_CODE_SCANNER_SCREEN,
    RECOVER_AN_ACCOUNT_SCREEN,
    RECOVER_PHRASE_SCREEN,
    SETTINGS_SCREEN,
    THEME_SCREEN,
    TRANSACTION_SIGNATURE_SCREEN,
    TRANSACTION_SUCCESS_SCREEN,
    WEBVIEW_PLATFORM_SCREEN,
    DEVELOPER_SETTINGS_SCREEN,
    ACCOUNT_STATUS_SCREEN,
    PASS_PHRASE_ACKNOWLEDGE_SCREEN,
    VIEW_PASSPHRASE_SCREEN,
    NODE_SETTINGS_SCREEN,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingBottomSheet(
    showSheet: Boolean,
    accounts: Int,
    launchQRScanScreen: Boolean = false,
    launchSettingsScreen: Boolean = false,
    launchAccountStatusScreen: Boolean = false,
    address: String? = null,
    onAccountDeleted: () -> Unit,
    onAlgoKitEvent: (event: AlgoKitEvent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    if (showSheet) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = {
                scope.launch {
                    scope
                        .async {
                            sheetState.hide()
                        }.await()
                    onAlgoKitEvent(AlgoKitEvent.ClOSE_BOTTOMSHEET)
                }
            },
            sheetState = sheetState,
            dragHandle = null,
        ) {
            OnBoardingBottomSheetNavHost(
                startDestination =
                    startDestination(
                        accounts,
                        launchQRScanScreen,
                        launchSettingsScreen,
                        launchAccountStatusScreen,
                    ),
                address = address,
                onAccountDeleted = {
                    onAccountDeleted()
                },
                closeSheet = {
                    scope.launch {
                        scope
                            .async {
                                sheetState.hide()
                            }.await()
                        onAlgoKitEvent(AlgoKitEvent.ClOSE_BOTTOMSHEET)
                    }
                },
            ) {
                onAlgoKitEvent(AlgoKitEvent.ALGO25_ACCOUNT_CREATED)
            }
        }
    }
}

@Composable
fun OnBoardingBottomSheetNavHost(
    startDestination: String = AlgoKitScreens.ON_BOARDING_ACCOUNT_TYPE_SCREEN.name,
    address: String?,
    closeSheet: () -> Unit,
    onAccountDeleted: () -> Unit,
    onFinish: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxHeight(.9f),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
    ) { padding ->
        Box(
            modifier = Modifier.background(color = AlgoKitTheme.colors.background).padding(0.dp),
        ) {
            NavHost(
                navController,
                enterTransition = { EnterTransition.None },
                exitTransition = { ExitTransition.None },
                startDestination = startDestination,
            ) {
                composable(route = AlgoKitScreens.INITIAL_REGISTER_INTRO_SCREEN.name) {
                    Column(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .background(color = AlgoKitTheme.colors.background)
                                .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                    ) {
                        InitialRegisterIntroScreen(navController)
                    }
                }
                composable(AlgoKitScreens.ON_BOARDING_ACCOUNT_TYPE_SCREEN.name) {
                    OnboardingAccountTypeScreen(navController) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
                composable(AlgoKitScreens.CREATE_ACCOUNT_NAME.name) {
                    CreateAccountNameScreen(
                        navController,
                        {
                            onFinish()
                        },
                    )
                }
                composable(AlgoKitScreens.HD_WALLET_SELECTION_SCREEN.name) {
                    HdWalletSelectionScreen(navController = navController)
                }

                composable(AlgoKitScreens.ACCOUNT_RECOVERY_TYPE_SCREEN.name) {
                    AccountRecoveryTypeSelectionScreen(navController = navController) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
                composable(AlgoKitScreens.QR_CODE_SCANNER_SCREEN.name) {
                    QRCodeScannerScreen(navController = navController, onQrScanned = {
                        coroutineScope.launch { snackbarHostState.showSnackbar(it) }
                    }, closeSheet = { closeSheet() })
                }
                composable(route = AlgoKitScreens.RECOVER_PHRASE_SCREEN.name + "/{mnemonic}") { it ->
                    it.arguments?.getString("mnemonic")?.let {
                        RecoveryPhraseScreen(navController = navController, it) {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar(it)
                            }
                        }
                    }
                }
                composable(route = AlgoKitScreens.RECOVER_AN_ACCOUNT_SCREEN.name) {
                    RecoverAnAccountScreen(navController = navController) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
                composable(route = AlgoKitScreens.WEBVIEW_PLATFORM_SCREEN.name) {
                    AlgoKitWebViewPlatformScreen(url = REPO_URL)
                }

                composable(route = AlgoKitScreens.TRANSACTION_SIGNATURE_SCREEN.name) {
                    navController.getData<KeyRegTransactionDetail>()?.let {
                        TransactionSignatureRequestScreen(navController = navController, it)
                    }
                }
                composable(route = AlgoKitScreens.TRANSACTION_SUCCESS_SCREEN.name) {
                    TransactionSuccessScreen {
                        closeSheet()
                    }
                }
                composable(route = AlgoKitScreens.INITIAL_REGISTER_INTRO_SCREEN.name) {
                    InitialRegisterIntroScreen(navController)
                }
                composable(route = AlgoKitScreens.SETTINGS_SCREEN.name) {
                    SettingsScreen(navController)
                }
                composable(route = AlgoKitScreens.THEME_SCREEN.name) {
                    ThemeScreen(navController)
                }
                composable(route = AlgoKitScreens.NODE_SETTINGS_SCREEN.name) {
                    NodeSettingsScreen(navController)
                }
                composable(route = AlgoKitScreens.DEVELOPER_SETTINGS_SCREEN.name) {
                    DeveloperSettingsScreen(navController) {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
                composable(route = AlgoKitScreens.ACCOUNT_STATUS_SCREEN.name) {
                    address?.let { it1 ->
                        AccountStatusScreen(navController, it1) {
                            onAccountDeleted()
                        }
                    }
                }
                composable(route = AlgoKitScreens.PASS_PHRASE_ACKNOWLEDGE_SCREEN.name) {
                    address?.let {
                        PassphraseAcknowledgeScreen(navController, it)
                    }
                }
                composable(route = AlgoKitScreens.VIEW_PASSPHRASE_SCREEN.name) {
                    address?.let { it1 ->
                        ViewPassphraseScreen(navController, it1)
                    }
                }
            }
        }
    }
}

fun startDestination(
    accounts: Int,
    qrScanFlow: Boolean,
    launchSettingsScreen: Boolean,
    launchAccountStatusScreen: Boolean,
): String =
    when {
        launchAccountStatusScreen -> AlgoKitScreens.ACCOUNT_STATUS_SCREEN.name
        launchSettingsScreen -> AlgoKitScreens.SETTINGS_SCREEN.name
        qrScanFlow -> AlgoKitScreens.QR_CODE_SCANNER_SCREEN.name
        accounts == 0 -> AlgoKitScreens.INITIAL_REGISTER_INTRO_SCREEN.name
        else -> AlgoKitScreens.ON_BOARDING_ACCOUNT_TYPE_SCREEN.name
    }
