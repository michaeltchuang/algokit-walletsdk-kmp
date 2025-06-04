package co.algorand.app.ui.screens

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.michaeltchuang.walletsdk.qr.presentation.view.AlgoKitQrScanner
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.michaeltchuang.walletsdk.qr.presentation.view.QrScanner
import com.michaeltchuang.walletsdk.qr.presentation.view.QrScannerViewEvent
import com.michaeltchuang.walletsdk.ui.theme.AlgoKitTheme
import kotlinx.coroutines.launch
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
