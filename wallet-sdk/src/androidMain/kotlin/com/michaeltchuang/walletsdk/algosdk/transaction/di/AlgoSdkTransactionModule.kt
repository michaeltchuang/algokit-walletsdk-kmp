 

package com.michaeltchuang.walletsdk.algosdk.transaction.di

/*import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.SignHdKeyTransactionImpl*/
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39Sdk
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoKitBip39SdkImpl
import org.koin.dsl.module


/*@Module
@InstallIn(SingletonComponent::class)
internal object AlgoSdkTransactionModule {

    @Provides
    fun provideSuggestedParamsMapper(mapper: SuggestedParamsMapperImpl): SuggestedParamsMapper = mapper

    @Provides
    fun provideAddAssetTransactionBuilder(
        impl: AddAssetTransactionBuilderBuilderImpl
    ): AddAssetTransactionBuilder = impl

    @Provides
    fun provideAlgoTransactionBuilder(impl: AlgoTransactionBuilderImpl): AlgoTransactionBuilder = impl

    @Provides
    fun provideAssetTransactionBuilder(
        impl: AddAssetTransactionBuilderBuilderImpl
    ): AddAssetTransactionBuilder = impl

    @Provides
    fun provideRekeyTransactionBuilder(impl: RekeyTransactionBuilderImpl): RekeyTransactionBuilder = impl

    @Provides
    fun provideRemoveAssetTransactionBuilder(
        impl: RemoveAssetTransactionBuilderImpl
    ): RemoveAssetTransactionBuilder = impl

    @Provides
    fun provideSendAndRemoveAssetTransactionMapper(
        impl: SendAndRemoveAssetTransactionBuilderImpl
    ): SendAndRemoveAssetTransactionBuilder = impl

    @Provides
    fun provideAlgoSdk(impl: AlgoSdkImpl): AlgoSdk = impl

    @Provides
    fun provideAlgoTransactionSigner(impl: AlgoTransactionSignerImpl): AlgoTransactionSigner = impl

    @Provides
    fun provideAlgoSdkAddress(impl: AlgoSdkAddressImpl): AlgoSdkAddress = impl

    @Provides
    fun providePeraBip39Sdk(impl: AlgoKitBip39SdkImpl): AlgoKitBip39Sdk = impl

    @Provides
    fun provideSignHdKeyTransaction(impl: SignHdKeyTransactionImpl): SignHdKeyTransaction = impl
}*/

val algoSdkTransactionModule = module {
    single { AlgoKitBip39SdkImpl() }
    single<AlgoKitBip39Sdk> { AlgoKitBip39SdkImpl() }
}
