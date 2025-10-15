package co.algorand.app.di

import co.algorand.app.AndroidApp
import com.michaeltchuang.walletsdk.core.algosdk.di.algoSdkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.koinConfiguration

actual fun nativeConfig() =
    koinConfiguration {
        androidLogger()
        androidContext(AndroidApp.instance)
        modules(algoSdkModule)
    }
