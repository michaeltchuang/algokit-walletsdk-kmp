package com.michaeltchuang.walletsdk.core.deeplink.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.michaeltchuang.walletsdk.core.transaction.signmanager.PendingTransactionRequestManger.storePendingTransactionRequest
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.ui.base.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.ui.base.navigation.AlgoKitScreens
import com.michaeltchuang.walletsdk.ui.qrscanner.viewmodels.QRScannerViewModel
import org.koin.compose.viewmodel.koinViewModel
import qrscanner.CameraLens
import qrscanner.QrScanner

@Composable
fun QRCodeScannerScreen(
    navController: NavController,
    onQrScanned: (String) -> Unit,
    closeSheet: () -> Unit,
) {
    val viewModel: QRScannerViewModel = koinViewModel()
    val hasProcessedResult = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.viewEvent.collect {
            when (it) {
                is QRScannerViewModel.ViewEvent.NavigateToRecoveryPhraseScreen -> {
                    navController.navigate(AlgoKitScreens.RECOVER_PHRASE_SCREEN.name + "/?mnemonic=${it.mnemonic}")
                }

                is QRScannerViewModel.ViewEvent.NavigateToTransactionSignatureRequestScreen -> {
                    storePendingTransactionRequest(it.txnDetail)
                    navController.navigate(AlgoKitScreens.TRANSACTION_SIGNATURE_SCREEN.name)
                }

                is QRScannerViewModel.ViewEvent.NavigateToSelectAccountScreen -> {
                    // Navigate to SelectAccountScreen with receiver address and amount
                    navController.navigate(
                        AlgoKitScreens.SELECT_ACCOUNT_SCREEN.name +
                            "?receiver=${it.assetTransfer.receiverAccountAddress}" +
                            "&amount=${it.assetTransfer.amount}",
                    )
                }

                is QRScannerViewModel.ViewEvent.ShowUnrecognizedDeeplink -> {
                    onQrScanned("Unrecognized QR Code")
                }
            }
        }
    }
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        QrScanner(
            modifier = Modifier.fillMaxSize(),
            flashlightOn = false,
            cameraLens = CameraLens.Back,
            openImagePicker = false,
            onCompletion = {
                if (!hasProcessedResult.value) {
                    viewModel.handleDeeplink(it)
                    hasProcessedResult.value = true
                }
            },
            imagePickerHandler = {},
            onFailure = {},
        )
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .background(color = AlgoKitTheme.colors.background),
        ) {
            AlgoKitTopBar(
                title = "Scan QR Code",
                modifier = Modifier.padding(start = 16.dp),
                onClick = {
                    if (navController.popBackStack().not()) {
                        closeSheet()
                    }
                },
            )
        }
    }
}
