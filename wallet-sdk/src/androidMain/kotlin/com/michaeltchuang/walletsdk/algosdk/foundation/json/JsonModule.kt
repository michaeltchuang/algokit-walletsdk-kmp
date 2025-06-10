 

package com.michaeltchuang.walletsdk.algosdk.foundation.json

import org.koin.android.logger.AndroidLogger
import org.koin.dsl.module

/*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object JsonModule {

    @Provides
    @Singleton
    fun provideJsonSerializer(impl: JsonSerializerImpl): JsonSerializer = impl
}
*/

val jsonModule = module {
    single { AndroidLogger() }
}