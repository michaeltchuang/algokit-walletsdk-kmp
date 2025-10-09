package com.michaeltchuang.walletsdk.foundation.di

import com.michaeltchuang.walletsdk.account.di.accountModules
import com.michaeltchuang.walletsdk.accountdetail.di.accountDetailModules
import com.michaeltchuang.walletsdk.deeplink.di.deepLinkModules
import com.michaeltchuang.walletsdk.settings.di.settingsModules

val walletSdkKoinModules =
    buildList {
        addAll(foundationModules)
        addAll(accountModules)
        addAll(accountDetailModules)
        addAll(deepLinkModules)
        addAll(settingsModules)
    }
