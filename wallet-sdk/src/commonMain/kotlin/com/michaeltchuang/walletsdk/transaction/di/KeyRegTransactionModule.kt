package com.michaeltchuang.walletsdk.transaction.di

import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetTransactionSigner
import com.michaeltchuang.walletsdk.transaction.data.BuildKeyRegOfflineTransactionImpl
import com.michaeltchuang.walletsdk.transaction.data.BuildKeyRegOnlineTransactionImpl
import com.michaeltchuang.walletsdk.transaction.data.TransactionsApiServiceImpl
import com.michaeltchuang.walletsdk.transaction.domain.usecase.BuildKeyRegOfflineTransaction
import com.michaeltchuang.walletsdk.transaction.domain.usecase.BuildKeyRegOnlineTransaction
import com.michaeltchuang.walletsdk.transaction.domain.usecase.CreateKeyRegTransaction
import com.michaeltchuang.walletsdk.transaction.domain.usecase.CreateKeyRegTransactionUseCase
import com.michaeltchuang.walletsdk.transaction.domain.usecase.GetTransactionParams
import com.michaeltchuang.walletsdk.transaction.domain.usecase.GetTransactionSignerUseCase
import com.michaeltchuang.walletsdk.transaction.domain.usecase.SendSignedTransactionUseCase
import com.michaeltchuang.walletsdk.transaction.signmanager.ExternalTransactionQueuingHelper
import com.michaeltchuang.walletsdk.transaction.signmanager.KeyRegTransactionSignManager
import org.koin.dsl.module

val keyRegTransactionModule =
    module {

        single { TransactionsApiServiceImpl(get()) }
        single {
            KeyRegTransactionSignManager(
                externalTransactionQueuingHelper = get(),
                getTransactionSigner = get(),
                getAlgo25SecretKey = get(),
                getFalcon24SecretKey = get(),
                getHdSeed = get(),
                getLocalAccount = get(),
            )
        }
        single { ExternalTransactionQueuingHelper() }
        single { GetTransactionSignerUseCase(get()) }
        single<GetTransactionSigner> { get<GetTransactionSignerUseCase>() }
        single { SendSignedTransactionUseCase(get()) }
        factory {
            GetTransactionParams(get<TransactionsApiServiceImpl>()::getTransactionParams)
        }
        single<BuildKeyRegOfflineTransaction> { BuildKeyRegOfflineTransactionImpl() }
        single<BuildKeyRegOnlineTransaction> { BuildKeyRegOnlineTransactionImpl() }

        single<CreateKeyRegTransaction> {
            CreateKeyRegTransactionUseCase(
                get(),
                get(),
                get(),
                get(),
            )
        }
    }
