package com.michaeltchuang.walletsdk.account.di

import com.michaeltchuang.walletsdk.account.data.database.dao.CustomAccountInfoDao
import com.michaeltchuang.walletsdk.account.data.database.dao.CustomHdSeedInfoDao
import com.michaeltchuang.walletsdk.account.data.mapper.entity.CustomAccountInfoEntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.entity.CustomAccountInfoEntityMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.entity.CustomHdSeedInfoEntityMapper
import com.michaeltchuang.walletsdk.account.data.mapper.entity.CustomHdSeedInfoEntityMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.CustomAccountInfoMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.CustomAccountInfoMapperImpl
import com.michaeltchuang.walletsdk.account.data.mapper.model.CustomHdSeedInfoMapper
import com.michaeltchuang.walletsdk.account.data.mapper.model.CustomHdSeedInfoMapperImpl
import com.michaeltchuang.walletsdk.account.data.repository.CustomAccountInfoRepositoryImpl
import com.michaeltchuang.walletsdk.account.data.repository.CustomHdSeedInfoRepositoryImpl
import com.michaeltchuang.walletsdk.account.domain.repository.custom.CustomAccountInfoRepository
import com.michaeltchuang.walletsdk.account.domain.repository.custom.CustomHdSeedInfoRepository
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.ClearAllCustomInformationUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.DeleteAccountCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.DeleteHdSeedCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAccountBackUpStatus
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAccountCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAccountCustomInfoOrNull
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAccountCustomName
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAccountsCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAccountsCustomInfoFlow
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAllAccountOrderIndexes
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetAllHdSeedOrderIndexes
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetBackedUpAccounts
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetHdSeedAsbBackUpStatus
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetHdSeedCustomInfoOrNull
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetHdSeedCustomName
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.GetNotBackedUpAccounts
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetAccountCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetAccountCustomName
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetAccountOrderIndex
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetAddressesBackedUp
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetHdSeedCustomInfo
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetHdSeedCustomName
import com.michaeltchuang.walletsdk.account.domain.usecase.custom.SetHdSeedOrderIndex
import com.michaeltchuang.walletsdk.foundation.database.AlgoKitDatabase
import org.koin.dsl.module

val accountCustomInfoModule =
    module {

        // DAOs
        single<CustomAccountInfoDao> { get<AlgoKitDatabase>().customAccountInfoDao() }
        single<CustomHdSeedInfoDao> { get<AlgoKitDatabase>().customHdSeedInfoDao() }

        // Mappers
        factory<CustomAccountInfoEntityMapper> { CustomAccountInfoEntityMapperImpl() }
        factory<CustomAccountInfoMapper> { CustomAccountInfoMapperImpl() }
        factory<CustomHdSeedInfoEntityMapper> { CustomHdSeedInfoEntityMapperImpl() }
        factory<CustomHdSeedInfoMapper> { CustomHdSeedInfoMapperImpl() }

        // Repositories
        single<CustomAccountInfoRepository> { CustomAccountInfoRepositoryImpl(get(), get(), get()) }
        single<CustomHdSeedInfoRepository> { CustomHdSeedInfoRepositoryImpl(get(), get(), get()) }

        // Use Cases - Account
        factory { SetAccountCustomInfo(get<CustomAccountInfoRepository>()::setCustomInfo) }
        factory { SetAccountCustomName(get<CustomAccountInfoRepository>()::setCustomName) }
        factory { GetAccountCustomName(get<CustomAccountInfoRepository>()::getCustomName) }
        factory { GetAccountsCustomInfoFlow(get<CustomAccountInfoRepository>()::getCustomInfoFlow) }
        factory { GetAccountsCustomInfo(get<CustomAccountInfoRepository>()::getCustomInfos) }
        factory { GetAccountCustomInfoOrNull(get<CustomAccountInfoRepository>()::getCustomInfoOrNull) }
        factory { GetAccountCustomInfo(get<CustomAccountInfoRepository>()::getCustomInfo) }
        factory { SetAccountOrderIndex(get<CustomAccountInfoRepository>()::setOrderIndex) }
        factory { DeleteAccountCustomInfo(get<CustomAccountInfoRepository>()::deleteCustomInfo) }
        factory { GetAllAccountOrderIndexes(get<CustomAccountInfoRepository>()::getAllAccountOrderIndexes) }
        factory { GetNotBackedUpAccounts(get<CustomAccountInfoRepository>()::getNotBackedUpAccounts) }
        factory { GetAccountBackUpStatus(get<CustomAccountInfoRepository>()::isAccountBackedUp) }
        factory { GetBackedUpAccounts(get<CustomAccountInfoRepository>()::getBackedUpAccounts) }
        factory { SetAddressesBackedUp(get<CustomAccountInfoRepository>()::setAddressesBackedUp) }

        // Use Cases - HD Seed
        factory { SetHdSeedCustomInfo(get<CustomHdSeedInfoRepository>()::setCustomInfo) }
        factory { SetHdSeedCustomName(get<CustomHdSeedInfoRepository>()::setCustomName) }
        factory { GetHdSeedCustomName(get<CustomHdSeedInfoRepository>()::getCustomName) }
        factory { GetHdSeedCustomInfoOrNull(get<CustomHdSeedInfoRepository>()::getCustomInfoOrNull) }
        factory { SetHdSeedOrderIndex(get<CustomHdSeedInfoRepository>()::setOrderIndex) }
        factory { DeleteHdSeedCustomInfo(get<CustomHdSeedInfoRepository>()::deleteCustomInfo) }
        factory { GetAllHdSeedOrderIndexes(get<CustomHdSeedInfoRepository>()::getAllHdSeedOrderIndexes) }
        factory { GetHdSeedAsbBackUpStatus(get<CustomHdSeedInfoRepository>()::isHdSeedBackedUp) }

        // Clear all
        factory { ClearAllCustomInformationUseCase(get(), get()) }
    }
