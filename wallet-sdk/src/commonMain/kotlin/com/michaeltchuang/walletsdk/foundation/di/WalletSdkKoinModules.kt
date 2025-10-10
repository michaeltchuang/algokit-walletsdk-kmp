package com.michaeltchuang.walletsdk.foundation.di

import com.michaeltchuang.walletsdk.account.di.accountModules
import com.michaeltchuang.walletsdk.accountdetail.di.accountDetailModules
import com.michaeltchuang.walletsdk.deeplink.di.deepLinkModules
import com.michaeltchuang.walletsdk.settings.di.settingsModules
import com.michaeltchuang.walletsdk.account.di.accountCoreModule
import com.michaeltchuang.walletsdk.account.di.customInfoModule
import com.michaeltchuang.walletsdk.account.di.localAccountsModule
import com.michaeltchuang.walletsdk.account.di.platformKoinModule
import com.michaeltchuang.walletsdk.account.di.viewModelModule
import com.michaeltchuang.walletsdk.deeplink.di.deepLinkModule
import com.michaeltchuang.walletsdk.foundation.commonModule
import com.michaeltchuang.walletsdk.foundation.delegateModule
import com.michaeltchuang.walletsdk.foundation.json.jsonModule
import com.michaeltchuang.walletsdk.network.di.networkModule
import com.michaeltchuang.walletsdk.transaction.di.keyRegTransactionModule

val walletSdkKoinModules =
    buildList {
        addAll(foundationModules)
        addAll(accountModules)
        addAll(accountDetailModules)
        addAll(deepLinkModules)
        addAll(settingsModules)
    }
