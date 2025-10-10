package com.michaeltchuang.walletsdk.account.di

import com.michaeltchuang.walletsdk.account.data.database.dao.Algo25Dao
import com.michaeltchuang.walletsdk.account.data.database.dao.Falcon24Dao
import com.michaeltchuang.walletsdk.account.data.database.dao.HdKeyDao
import com.michaeltchuang.walletsdk.account.data.database.dao.HdSeedDao
import com.michaeltchuang.walletsdk.account.data.mapper.entity.Algo25EntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.entity.Algo25EntityMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.entity.Falcon24EntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.entity.Falcon24EntityMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.entity.HdKeyEntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.entity.HdKeyEntityMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.entity.HdSeedEntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.entity.HdSeedEntityMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.Algo25Mapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.Algo25MapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.Falcon24Mapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.Falcon24MapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.Falcon24WalletSummaryMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.Falcon24WalletSummaryMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdKeyMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdKeyMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdSeedMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdSeedMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdSeedWalletSummaryMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdSeedWalletSummaryMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdWalletSummaryMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.HdWalletSummaryMapperImpl
import com.michaeltchuang.walletsdk.account.data.repository.Algo25AccountRepositoryImpl
import com.michaeltchuang.walletsdk.account.data.repository.Falcon24AccountRepositoryImpl
import com.michaeltchuang.walletsdk.account.data.repository.HdKeyAccountRepositoryImpl
import com.michaeltchuang.walletsdk.account.data.repository.HdSeedRepositoryImpl
import com.michaeltchuang.walletsdk.account.domain.repository.local.Algo25AccountRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.Falcon24AccountRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdKeyAccountRepository
import com.michaeltchuang.walletsdk.account.domain.repository.local.HdSeedRepository
import com.michaeltchuang.walletsdk.account.domain.usecase.core.AddHdKeyAccount
import com.michaeltchuang.walletsdk.account.domain.usecase.core.AddHdKeyAccountUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.core.AddHdSeed
import com.michaeltchuang.walletsdk.account.domain.usecase.core.AddHdSeedUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetAlgo25SecretKey
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetFalcon24SecretKey
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetFalcon24WalletSummaries
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetHdEntropy
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetHdSeed
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetHdWalletSummaries
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetMaxHdSeedId
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetSeedIdIfExistingEntropy
import com.michaeltchuang.walletsdk.account.domain.usecase.local.SaveAlgo25Account
import com.michaeltchuang.walletsdk.account.domain.usecase.local.SaveFalcon24Account
import com.michaeltchuang.walletsdk.account.domain.usecase.local.SaveHdKeyAccount
import com.michaeltchuang.walletsdk.foundation.database.AlgoKitDatabase
import org.koin.dsl.module

val localAccountsModule =
    module {

        single<Algo25Dao> { get<AlgoKitDatabase>().algo25Dao() }
        single<Algo25EntityMapper> { Algo25EntityMapperImpl() }
        single<Algo25Mapper> { Algo25MapperImpl() }

        single<Algo25AccountRepository> {
            Algo25AccountRepositoryImpl(get(), get(), get(), get())
        }

        factory { SaveAlgo25Account(get<Algo25AccountRepository>()::addAccount) }
        factory { GetAlgo25SecretKey(get<Algo25AccountRepository>()::getSecretKey) }

        single<Falcon24Dao> { get<AlgoKitDatabase>().falcon24Dao() }
        single<Falcon24EntityMapper> { Falcon24EntityMapperImpl() }
        single<Falcon24Mapper> { Falcon24MapperImpl() }

        single<Falcon24AccountRepository> {
            Falcon24AccountRepositoryImpl(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }

        factory { SaveFalcon24Account(get<Falcon24AccountRepository>()::addAccount) }
        factory { GetFalcon24SecretKey(get<Falcon24AccountRepository>()::getSecretKey) }
        single { GetFalcon24WalletSummaries(get<Falcon24AccountRepository>()::getHdWalletSummaries) }
        single<Falcon24WalletSummaryMapper> { Falcon24WalletSummaryMapperImpl() }
        single<Falcon24Mapper> { Falcon24MapperImpl() }

        single<HdKeyDao> { get<AlgoKitDatabase>().hdKeyDao() }
        single<HdKeyEntityMapper> { HdKeyEntityMapperImpl() }
        single<HdWalletSummaryMapper> { HdWalletSummaryMapperImpl() }
        single<HdKeyMapper> { HdKeyMapperImpl() }

        single<HdKeyAccountRepository> {
            HdKeyAccountRepositoryImpl(
                get(),
                get(),
                get(),
                get(),
                get(),
                get(),
            )
        }

        single { SaveHdKeyAccount(get<HdKeyAccountRepository>()::addAccount) }
        single { GetHdWalletSummaries(get<HdKeyAccountRepository>()::getHdWalletSummaries) }

        single<AddHdKeyAccount> { AddHdKeyAccountUseCase(get(), get()) }

        single<HdSeedDao> { get<AlgoKitDatabase>().hdSeedDao() }
        single<HdSeedEntityMapper> { HdSeedEntityMapperImpl() }
        single<HdSeedMapper> { HdSeedMapperImpl() }
        single<HdSeedRepository> { HdSeedRepositoryImpl(get(), get(), get(), get()) }
        factory { GetSeedIdIfExistingEntropy(get<HdSeedRepository>()::getSeedIdIfExistingEntropy) }
        single<AddHdSeed> { AddHdSeedUseCase(get(), get(), get()) }
        single { GetMaxHdSeedId(get<HdSeedRepository>()::getMaxSeedId) }
        single { GetHdEntropy(get<HdSeedRepository>()::getEntropy) }
        single<HdSeedWalletSummaryMapper> { HdSeedWalletSummaryMapperImpl() }
        single { GetHdSeed(get<HdSeedRepository>()::getSeed) }
    }
