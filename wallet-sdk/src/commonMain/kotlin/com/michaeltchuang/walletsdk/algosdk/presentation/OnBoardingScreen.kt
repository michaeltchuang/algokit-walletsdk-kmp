package com.michaeltchuang.walletsdk.algosdk.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

enum class AlgoKitEvent {
    CLOSE_BOTTOM_SHEET,
    HD_ACCOUNT_CREATED,
    ALGO25_ACCOUNT_CREATED,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingBottomSheet(onAlgoKitEvent: (event: AlgoKitEvent) -> Unit) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = {
            onAlgoKitEvent(AlgoKitEvent.CLOSE_BOTTOM_SHEET)
        },
        sheetState = sheetState,
        dragHandle = null,
    ) {
        AlgoKitBip39Screen {
            when (it) {
                AlgoKitEvent.CLOSE_BOTTOM_SHEET -> {
                    scope.launch(Dispatchers.Main) {
                        sheetState.hide()
                        onAlgoKitEvent(it)
                    }
                }
                AlgoKitEvent.HD_ACCOUNT_CREATED,
                AlgoKitEvent.ALGO25_ACCOUNT_CREATED,
                -> {
                    onAlgoKitEvent(it)
                }
            }
        }
    }
}
