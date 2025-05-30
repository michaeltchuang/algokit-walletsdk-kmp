package co.algorand.app.ui.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import co.algorand.app.ui.screens.DiscoverScreen
import co.algorand.app.ui.screens.accounts.AccountsScreen
import co.algorand.app.ui.screens.SettingsScreen
import co.algorand.app.ui.widgets.snackbar.SnackBarLayout
import co.algorand.app.ui.widgets.snackbar.SnackbarViewModel
import org.koin.compose.viewmodel.koinNavViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
fun NavGraphBuilder.getBottomNavigationGraph(
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    composable<Accounts> {
        val backStackEntry = remember(it) { navController.getBackStackEntry<Accounts>() }
        val sharedViewModel: SnackbarViewModel = koinNavViewModel(viewModelStoreOwner = backStackEntry)
        AccountsScreen(
            tag = backStackEntry.toRoute<Accounts>().details.name,
            navController = navController,
            snackbarViewModel = sharedViewModel,
        )
        SnackBarLayout(sharedViewModel, snackbarHostState)
    }
    composable<Discover> {
        val backStackEntry = remember(it) { navController.getBackStackEntry<Discover>() }
        val sharedViewModel: SnackbarViewModel = koinNavViewModel(viewModelStoreOwner = backStackEntry)
        DiscoverScreen(
            tag = backStackEntry.toRoute<Discover>().details.name,
            navController = navController,
            snackbarViewModel = sharedViewModel,
        )
        SnackBarLayout(sharedViewModel, snackbarHostState)
    }
    composable<Settings> {
        val backStackEntry = remember(it) { navController.getBackStackEntry<Settings>() }
        val sharedViewModel: SnackbarViewModel = koinNavViewModel(viewModelStoreOwner = backStackEntry)
        SettingsScreen(
            tag = backStackEntry.toRoute<Settings>().details.name,
            navController = navController,
            snackbarViewModel = sharedViewModel,
        )
        SnackBarLayout(sharedViewModel, snackbarHostState)
    }
}
