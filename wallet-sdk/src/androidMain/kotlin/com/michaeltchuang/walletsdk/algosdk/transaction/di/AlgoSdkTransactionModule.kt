/*
 * Copyright 2022-2025 Pera Wallet, LDA
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

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
