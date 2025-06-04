package co.algorand.app.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.michaeltchuang.walletsdk.qr.presentation.view.AlgoKitQrScanner
import kotlinx.serialization.Serializable

@Serializable
data object QrScannerScreenNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QrScannerScreen(scannedQrResult: (String) -> Unit) {
    AlgoKitQrScanner {
        scannedQrResult(it)
    }
}
