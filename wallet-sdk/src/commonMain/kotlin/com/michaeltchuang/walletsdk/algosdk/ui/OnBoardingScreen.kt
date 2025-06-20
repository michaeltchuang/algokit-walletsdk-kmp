package com.michaeltchuang.walletsdk.algosdk.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


enum class AlgoKitEvent() {
    ClOSE,
    ACCOUNT_CREATED
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingBottomSheet(event: (event: AlgoKitEvent) -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            event(AlgoKitEvent.ClOSE)
        },
        sheetState = sheetState,
        dragHandle = null
    ) {
        AlgoKitBip39Screen {
            when (it) {
                AlgoKitEvent.ClOSE -> {
                    scope.launch(Dispatchers.Main) {
                        sheetState.hide()
                        event(AlgoKitEvent.ClOSE)
                    }
                }

                AlgoKitEvent.ACCOUNT_CREATED -> {
                    event(AlgoKitEvent.ACCOUNT_CREATED)
                }
            }
        }
    }
}
