package com.michaeltchuang.walletsdk.ui

/**
 * Android implementation: checks if debuggable flag is set in the app.
 */
actual fun isDebugBuild(): Boolean {
    // Use the generated BuildInfo.DEBUG for Android
    return BuildInfo.DEBUG
}
