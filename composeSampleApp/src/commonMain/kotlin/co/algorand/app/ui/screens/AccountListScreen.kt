package co.algorand.app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import co.algorand.app.ui.components.AccountItem
import co.algorand.app.ui.components.LottieConfetti
import co.algorand.app.ui.navigation.ACTIONS
import co.algorand.app.ui.viewmodel.AccountListViewModel
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.account.domain.model.custom.AccountLite
import com.michaeltchuang.walletsdk.account.presentation.components.AlgoKitEvent
import com.michaeltchuang.walletsdk.account.presentation.components.OnBoardingBottomSheet
import com.michaeltchuang.walletsdk.utils.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

private const val CONFETTI_DURATION = 5000L
private val FAB_PADDING = 32.dp
private const val TAG = "AccountListScreen"

@Composable
fun AccountListScreen(
    navController: NavController,
    snackbarViewModel: SnackbarViewModel,
    tag: String,
) {
    val viewModel: AccountListViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val events = viewModel.viewEvent.collectAsStateWithLifecycle(initialValue = null)
    var showSheet by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var showConfetti by remember { mutableStateOf(false) }
    var qrScanClick by rememberSaveable { mutableStateOf(false) }
    var settingsClick by rememberSaveable { mutableStateOf(false) }
    var onAccountItemClick by rememberSaveable { mutableStateOf(false) }
    var address by rememberSaveable { mutableStateOf("") }
    // val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        ACTIONS.qrClickEvent.collect {
            qrScanClick = it
            settingsClick = false
            onAccountItemClick = false
            showSheet = it
        }
    }
    LaunchedEffect(Unit) {
        ACTIONS.settingsClickEvent.collect {
            qrScanClick = false
            onAccountItemClick = false
            settingsClick = it
            showSheet = it
        }
    }
    LaunchedEffect(Unit) {
        viewModel.fetchAccounts()
    }

    /*  LaunchedEffect(events.value) {
          events.value?.let { event ->
              handleEvent(event, context)
          }
      }*/

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    settingsClick = false
                    qrScanClick = false
                    onAccountItemClick = false
                    showSheet = true
                },
                modifier = Modifier.padding(end = FAB_PADDING, bottom = FAB_PADDING),
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Account")
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
    ) { padding ->
        AccountListContent(
            state = state,
            onAccountItemClick = { it ->
                scope.launch {
                    address = it
                    onAccountItemClick = true
                    showSheet = true
                    // viewModel.deleteAccount(address)
                }
            },
        )
    }

    OnBoardingBottomSheet(
        showSheet = showSheet,
        accounts = viewModel.accountLite.size,
        launchQRScanScreen = qrScanClick,
        launchSettingsScreen = settingsClick,
        launchAccountStatusScreen = onAccountItemClick,
        address = address,
        onAccountDeleted = {
            showSheet = false
            viewModel.fetchAccounts()
        },
    ) { event ->
        handleBottomSheetEvent(
            event = event,
            onCloseSheet = { showSheet = false },
            onAccountCreated = {
                showConfetti = true
                showSheet = false
                scope.launch {
                    viewModel.fetchAccounts()
                }
            },
        )
    }

    if (showConfetti) {
        ConfettiEffect(
            onComplete = { showConfetti = false },
        )
    }
}

@Composable
private fun AccountListContent(
    state: AccountListViewModel.AccountsState,
    onAccountItemClick: (String) -> Unit,
) {
    when (state) {
        AccountListViewModel.AccountsState.Idle -> {
            CenteredMessage("Ready to load accounts...")
        }

        AccountListViewModel.AccountsState.Loading -> {
            CenteredLoader()
        }

        is AccountListViewModel.AccountsState.Content -> {
            AccountsList(
                accounts = state.accounts,
                onAccountItemClick = onAccountItemClick,
            )
        }

        is AccountListViewModel.AccountsState.Error -> {
            CenteredMessage(
                text = "Error: ${state.message}",
                color = Color.Red,
            )
        }
    }
}

@Composable
private fun CenteredMessage(
    text: String,
    color: Color = Color.Unspecified,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = text, color = color)
    }
}

@Composable
private fun CenteredLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun AccountsList(
    accounts: List<AccountLite>,
    onAccountItemClick: (String) -> Unit,
) {
    if (accounts.isEmpty()) {
        CenteredMessage("No accounts found. Tap '+' to add one!")
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                accounts,
            ) { account ->
                AccountItem(account) { address ->
                    onAccountItemClick(address)
                }
                Log.d(TAG, "Total accounts: ${accounts.size}")
            }
        }
    }
}

@Composable
private fun ConfettiEffect(onComplete: () -> Unit) {
    LottieConfetti(
        modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
    )

    LaunchedEffect(Unit) {
        delay(CONFETTI_DURATION)
        onComplete()
    }
}

/*private fun handleEvent(
    event: AccountsEvent,
    context: android.content.Context,
) {
    when (event) {
        is AccountsEvent.ShowError -> {
            Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
        }

        is AccountsEvent.ShowMessage -> {
            Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
        }
    }
}*/

private fun handleBottomSheetEvent(
    event: AlgoKitEvent,
    onCloseSheet: () -> Unit,
    onAccountCreated: () -> Unit,
) {
    when (event) {
        AlgoKitEvent.ClOSE_BOTTOMSHEET -> {
            onCloseSheet()
        }

        AlgoKitEvent.ALGO25_ACCOUNT_CREATED,
        AlgoKitEvent.HD_ACCOUNT_CREATED,
        -> {
            onAccountCreated()
        }
    }
}
