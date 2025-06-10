package co.algorand.app.ui.screens.accounts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.algosdk.ui.AlgoKitBip39Screen
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    navController: NavController,
    snackbarViewModel: SnackbarViewModel,
    tag: String,
    accountsViewModel: AccountsViewModel = koinViewModel()
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showSheet by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text(text = "Accounts Screen")
            Button(onClick = {
                showSheet = true
            }) {
                Text(text = "AlgoKit WalletSDK BottomSheet")
            }
        }
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = sheetState
        ) {
            AlgoKitBip39Screen("borrow among leopard smooth trade cake profit proud matrix bottom goose charge oxygen shine punch hotel era monitor fossil violin tip notice any visit")
        }
    }
}
