package com.michaeltchuang.walletsdk.foundation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.dsl.module

val commonModule = module {

    // Provide a Dispatcher.IO instance
    factory<CoroutineDispatcher> { Dispatchers.IO }

/*    // Provide LifecycleAwareManager via its implementation
    factory<LifecycleAwareManager> { LifecycleAwareManagerImpl() }*/
}

val delegateModule = module {
    factory { StateDelegate<Any>() } // Generic; use with type casting in ViewModel
    factory { EventDelegate<Any>() }
}
