 

package com.michaeltchuang.walletsdk.algosdk.foundation.manager

import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

internal class LifecycleAwareManagerImpl @Inject constructor() : LifecycleAwareManager {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var currentJob: Job? = null
    private var initializationJob: Job? = null

    private var listener: LifecycleAwareManager.LifecycleAwareManagerListener? = null

    override fun onResume(owner: LifecycleOwner) {
        initializationJob = coroutineScope.launch(Dispatchers.IO) {
            listener?.onInitializeManager(this)
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        stop()
    }

    override fun onDestroy(owner: LifecycleOwner) {
        clearResources()
        listener?.onClearResources()
    }

    override fun launchScope(action: suspend CoroutineScope.() -> Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            action()
        }
    }

    override fun startJob() {
        currentJob = coroutineScope.launch(Dispatchers.IO) {
            listener?.onStartJob(this)
        }
    }

    override fun setListener(listener: LifecycleAwareManager.LifecycleAwareManagerListener) {
        this.listener = listener
    }

    override fun stopCurrentJob() {
        if (currentJob?.isActive == true) {
            currentJob?.cancel()
        }
    }

    private fun stop() {
        currentJob?.cancel()
        currentJob = null
        initializationJob?.cancel()
        initializationJob = null
        coroutineScope.coroutineContext.cancelChildren()
    }

    private fun clearResources() {
        currentJob = null
        initializationJob = null
        coroutineScope.coroutineContext.cancelChildren()
    }
}
