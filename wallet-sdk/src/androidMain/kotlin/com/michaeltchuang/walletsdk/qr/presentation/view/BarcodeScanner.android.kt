package com.michaeltchuang.walletsdk.qr.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.michaeltchuang.walletsdk.foundation.AlgoKitResult
import com.michaeltchuang.walletsdk.qr.presentation.view.QrScannerViewEvent.PauseCamera
import com.michaeltchuang.walletsdk.qr.presentation.view.QrScannerViewEvent.ResumeCamera
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import com.michaeltchuang.walletsdk.qr.presentation.view.QrScannerViewEvent

@Composable
internal actual fun BarcodeScanner(
    modifier: Modifier,
    viewEvent: State<QrScannerViewEvent>,
    onResult: (AlgoKitResult<String>) -> Unit
) {
    val barcodeCallback = remember { getBarcodeCallback(onResult) }
    AndroidView(
        modifier = modifier,
        factory = { context ->
            BarcodeView(context).apply {
                decoderFactory = DefaultDecoderFactory(mutableListOf(BarcodeFormat.QR_CODE))
            }
        },
        update = {
            when (viewEvent.value) {
                PauseCamera -> {
                    it.stopDecoding()
                    it.pause()
                }
                ResumeCamera -> {
                    it.decodeContinuous(barcodeCallback)
                    it.resume()
                }
            }
        }
    )
}

private fun getBarcodeCallback(onResult: (AlgoKitResult<String>) -> Unit): BarcodeCallback {
    return BarcodeCallback { result ->
        if (result.text != null) {
            onResult(AlgoKitResult.Success(result.text))
        } else {
            onResult(AlgoKitResult.Error(IllegalArgumentException()))
        }
    }
}
