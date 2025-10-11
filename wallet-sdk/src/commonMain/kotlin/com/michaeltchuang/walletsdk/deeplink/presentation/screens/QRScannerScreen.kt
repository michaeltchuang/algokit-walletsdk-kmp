package com.michaeltchuang.walletsdk.deeplink.presentation.screens

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
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitScreens
import com.michaeltchuang.walletsdk.deeplink.presentation.viewmodels.QRScannerViewModel
import com.michaeltchuang.walletsdk.foundation.designsystem.theme.AlgoKitTheme
import com.michaeltchuang.walletsdk.foundation.designsystem.widget.AlgoKitTopBar
import com.michaeltchuang.walletsdk.foundation.utils.navigateWithArgument
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
                    navController.navigateWithArgument(
                        AlgoKitScreens.TRANSACTION_SIGNATURE_SCREEN.name,
                        it.txnDetail,
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
