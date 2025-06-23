package com.michaeltchuang.walletsdk.qr.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import qrscanner.CameraLens
import qrscanner.QrScanner

@Composable
fun AlgoKitQrScanner(
    modifier: Modifier = Modifier,
    onQrScanned: (String) -> Unit,
) {
    Box(modifier = modifier.fillMaxSize()) {
        QrScanner(
            modifier = modifier,
            flashlightOn = false,
            cameraLens = CameraLens.Back,
            openImagePicker = false,
            onCompletion = {
                onQrScanned(it)
            },
            imagePickerHandler = {},
            onFailure = {},
        )
    }
}
