package com.michaeltchuang.walletsdk.ui.base.webview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState

@Composable
fun AlgoKitWebViewScreen(
    modifier: Modifier,
    url: String,
) {
    WebView(rememberWebViewState(url = url), modifier = modifier)
}
