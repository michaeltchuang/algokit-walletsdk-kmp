package com.michaeltchuang.walletsdk.demo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.michaeltchuang.walletsdk.core.foundation.webview.AlgoKitWebViewScreen
import com.michaeltchuang.walletsdk.demo.ui.widgets.snackbar.SnackbarViewModel
import com.michaeltchuang.walletsdk.demo.utils.DemoAppConstants.REPO_URL
import com.michaeltchuang.walletsdk.ui.base.designsystem.theme.AlgoKitTheme

@Composable
fun DiscoverScreen(
    navController: NavController,
    snackbarViewModel: SnackbarViewModel,
    tag: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier =
            Modifier
                .fillMaxSize()
                .background(AlgoKitTheme.colors.background),
    ) {
        AlgoKitWebViewScreen(
            modifier = Modifier.fillMaxSize(),
            REPO_URL,
        )
    }
}
