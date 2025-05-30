package com.michaeltchuang.walletsdk.qr.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.michaeltchuang.walletsdk.foundation.AlgoKitResult
import com.michaeltchuang.walletsdk.qr.presentation.view.QrScannerViewEvent

@Composable
internal actual fun BarcodeScanner(
    modifier: Modifier,
    viewEvent: State<QrScannerViewEvent>,
    onResult: (AlgoKitResult<String>) -> Unit
) {
}
