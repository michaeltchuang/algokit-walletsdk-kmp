package com.michaeltchuang.walletsdk.account.di

import com.michaeltchuang.walletsdk.account.domain.usecase.core.AccountAdditionUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.core.AddAlgo25Account
import com.michaeltchuang.walletsdk.account.domain.usecase.core.AddAlgo25AccountUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.core.DeleteAlgo25AccountUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.core.DeleteHdKeyAccountUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.core.GetAccountRegistrationTypeUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.core.GetLocalAccountUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.core.NameRegistrationUseCase
import com.michaeltchuang.walletsdk.account.domain.usecase.local.DeleteAlgo25Account
import com.michaeltchuang.walletsdk.account.domain.usecase.local.GetLocalAccounts
import org.koin.dsl.module

val accountCoreModule = module {
    single { AddAlgo25AccountUseCase(get(), get()) }

    factory<AddAlgo25Account> { get<AddAlgo25AccountUseCase>() }
    single { AccountAdditionUseCase(get(), get(), get()) }

    single { DeleteAlgo25AccountUseCase(get(), get()) }
    single<DeleteAlgo25Account> { get<DeleteAlgo25AccountUseCase>() }

    single { DeleteHdKeyAccountUseCase(get(), get(), get()) }

    single { GetLocalAccountUseCase(get(), get(), get()) }
    single<GetLocalAccounts> { get<GetLocalAccountUseCase>() }
    single { GetAccountRegistrationTypeUseCase(get()) }

    single { NameRegistrationUseCase(get(), get(), get(), get(), get(), get()) }
    /*    single<AccountCreationHdKeyTypeMapper> { DefaultAccountCreationHdKeyTypeMapperImpl(get()) }

        single { RecoverPassphraseUseCase(get(), get()) }*/
}
