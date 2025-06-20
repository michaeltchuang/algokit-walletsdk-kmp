package co.algorand.app.ui.screens.accounts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.algosdk.ui.AlgoKitEvent
import com.michaeltchuang.walletsdk.algosdk.ui.OnBoardingBottomSheet
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsScreen(
    navController: NavController,
    snackbarViewModel: SnackbarViewModel,
    tag: String,
    accountsViewModel: AccountsViewModel = koinViewModel(),
) {
    var showSheet by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
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
        OnBoardingBottomSheet {
            when (it) {
                AlgoKitEvent.CLOSE_BOTTOM_SHEET -> {
                    showSheet = false
                }
                AlgoKitEvent.HD_ACCOUNT_CREATED,
                AlgoKitEvent.ALGO25_ACCOUNT_CREATED -> { }
            }
        }
    }
}
