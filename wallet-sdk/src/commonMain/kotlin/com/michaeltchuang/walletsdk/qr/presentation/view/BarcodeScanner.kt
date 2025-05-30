package com.michaeltchuang.walletsdk.qr.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.michaeltchuang.walletsdk.foundation.AlgoKitResult

@Composable
internal expect fun BarcodeScanner(
    modifier: Modifier = Modifier,
    viewEvent: State<QrScannerViewEvent>,
    onResult: (AlgoKitResult<String>) -> Unit
)
