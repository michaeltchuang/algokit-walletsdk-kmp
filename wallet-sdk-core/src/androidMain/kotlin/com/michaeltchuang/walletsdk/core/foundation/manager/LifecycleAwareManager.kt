package com.michaeltchuang.walletsdk.core.foundation.manager

import androidx.lifecycle.DefaultLifecycleObserver
import kotlinx.coroutines.CoroutineScope

interface LifecycleAwareManager : DefaultLifecycleObserver {
    fun stopCurrentJob()

    fun startJob()

    fun setListener(listener: LifecycleAwareManagerListener)

    fun launchScope(action: suspend CoroutineScope.() -> Unit)

    interface LifecycleAwareManagerListener {
        suspend fun onInitializeManager(coroutineScope: CoroutineScope) {}

        suspend fun onStartJob(coroutineScope: CoroutineScope) {}

        fun onClearResources() {}
    }
}
