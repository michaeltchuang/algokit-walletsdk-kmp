package com.michaeltchuang.walletsdk.qr.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
data object QrScannerNavigation

sealed interface QrScannerViewEvent {
    data object PauseCamera : QrScannerViewEvent
    data object ResumeCamera : QrScannerViewEvent
}

@Composable
fun QrScanner(
    viewEvent: State<QrScannerViewEvent>,
    modifier: Modifier = Modifier,
    onQrScanned: (String) -> Unit
) {
    Box(modifier = modifier.fillMaxSize()) {
        BarcodeScanner(viewEvent = viewEvent) { result ->
            result.getDataOrNull()?.let(onQrScanned)
        }
        CameraOverlay()
    }
}
