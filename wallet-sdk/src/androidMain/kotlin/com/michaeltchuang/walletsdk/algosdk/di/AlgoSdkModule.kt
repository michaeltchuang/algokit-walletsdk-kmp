 

package com.michaeltchuang.walletsdk.algosdk.di
import com.michaeltchuang.walletsdk.algosdk.domain.usecase.RecoverWithPassphrasePreviewUseCase
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoAccountSdkImpl
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39Sdk
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39SdkImpl
import org.koin.dsl.module

/*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object AlgoSdkModule {

    @Provides
    @Singleton
    fun provideAlgoAccountSdk(impl: AlgoAccountSdkImpl): AlgoAccountSdk = impl
}
*/

val algoSdkModule = module {
    single { AlgoAccountSdkImpl() }
    single<AlgoKitBip39Sdk> { AlgoKitBip39SdkImpl() }
    factory { RecoverWithPassphrasePreviewUseCase(get()) }
}

