package com.michaeltchuang.walletsdk.core.foundation.webview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.final_class.webview_multiplatform_mobile.webview.WebViewPlatform
import com.final_class.webview_multiplatform_mobile.webview.controller.rememberWebViewController
import kotlinx.serialization.Serializable

@Serializable
data object WebViewPlatformScreenNavigation

@Composable
fun AlgoKitWebViewPlatformScreen(url: String) {
    val webViewController by rememberWebViewController()
    WebViewPlatform(webViewController = webViewController)
    LaunchedEffect(Unit) {
        webViewController.open(url)
    }
}
