 

/*
package com.michaeltchuang.walletsdk.algosdk.foundation.network.di

import com.michaeltchuang.walletsdk.algosdk.foundation.json.JsonSerializer
import com.michaeltchuang.walletsdk.algosdk.foundation.network.exceptions.PeraRetrofitErrorHandler
import com.michaeltchuang.walletsdk.algosdk.foundation.network.exceptions.PeraRetrofitErrorHandlerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkCommonModule {

    @Provides
    @Singleton
    internal fun providePeraExceptionHandler(jsonSerializer: JsonSerializer): PeraRetrofitErrorHandler {
        return PeraRetrofitErrorHandlerImpl(jsonSerializer)
    }
}
*/
