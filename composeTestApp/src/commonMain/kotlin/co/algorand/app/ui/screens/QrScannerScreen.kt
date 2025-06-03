package co.algorand.app.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
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
fun QrScannerScreen() {
    val cameraState = mutableStateOf(QrScannerViewEvent.ResumeCamera)
    var scannedQr by remember { mutableStateOf("") }

    val bottomSheetState = rememberStandardBottomSheetState(
        skipHiddenState = false,
        initialValue = SheetValue.Hidden
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
                Text(
                    modifier = Modifier.padding(16.dp).fillMaxWidth(),
                    style = AlgoKitTheme.typography.body.large.sansMedium,
                    text = scannedQr,
                    textAlign = TextAlign.Center
                )
        },
    ) {
        QrScanner(
            viewEvent = cameraState
        ) {
            scannedQr = it
            scope.launch { scaffoldState.bottomSheetState.expand() }
        }
    }
}
