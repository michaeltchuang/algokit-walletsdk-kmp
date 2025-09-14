package com.michaeltchuang.walletsdk.utils

import androidx.navigation.NavController

fun ByteArray.clearFromMemory(): ByteArray {
    // Overwrite the byte array contents with zeros
    this.fill(0)
    return ByteArray(0)
}

/*fun Context.getTextFromClipboard(): String? {
    val clipboard = ContextCompat.getSystemService(this, ClipboardManager::class.java) ?: return ""
    return clipboard.getTextFromClipboard()
}

fun ClipboardManager.getTextFromClipboard(): String? {
    val firstClip = primaryClip?.getItemAt(0)?.text?.toString().orEmpty()
    return when (getClipboardMimeType(primaryClipDescription)) {
        MIMETYPE_TEXT_PLAIN -> firstClip
        MIMETYPE_TEXT_HTML -> HtmlCompat.fromHtml(firstClip, HtmlCompat.FROM_HTML_MODE_COMPACT)
            .toString()

        else -> null
    }
}

private fun getClipboardMimeType(clipDescription: ClipDescription?): String? {
    return clipDescription?.let { safeClipDescription ->
        when {
            safeClipDescription.hasMimeType(MIMETYPE_TEXT_HTML) -> MIMETYPE_TEXT_HTML
            safeClipDescription.hasMimeType(MIMETYPE_TEXT_PLAIN) -> MIMETYPE_TEXT_PLAIN
            else -> null
        }
    }
}*/


fun <T> NavController.navigateWithArgument(route: String, bundle: T) {
    setData(bundle)
    navigate(route)
}

private fun <T> NavController.setData(data: T) {
    currentBackStackEntry
        ?.savedStateHandle
        ?.set("data", data)
}

fun <T> NavController.getData(): T? {
    return this.previousBackStackEntry
        ?.savedStateHandle
        ?.get<T>("data")
}
