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

package com.michaeltchuang.walletsdk.algosdk.encryption.di

import com.michaeltchuang.walletsdk.algosdk.encryption.data.manager.Base64ManagerImpl
import com.michaeltchuang.walletsdk.algosdk.encryption.domain.manager.AESPlatformManager
import com.michaeltchuang.walletsdk.algosdk.encryption.domain.manager.AESPlatformManagerImpl
import com.michaeltchuang.walletsdk.algosdk.encryption.domain.manager.Base64Manager
/*import com.michaeltchuang.walletsdk.algosdk.encryption.domain.usecase.AndroidEncryptionManagerImpl*/
import com.michaeltchuang.walletsdk.algosdk.encryption.domain.usecase.GetEncryptionSecretKey
import com.michaeltchuang.walletsdk.algosdk.transaction.sdk.AlgoAccountSdkImpl
import org.koin.dsl.module

/*import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object EncryptionModule {

    @Provides
    @Singleton
    fun provideBase64Manager(impl: Base64ManagerImpl): Base64Manager = impl

    @Provides
    @Singleton
    fun provideAESPlatformManager(impl: AESPlatformManagerImpl): AESPlatformManager = impl
}*/
