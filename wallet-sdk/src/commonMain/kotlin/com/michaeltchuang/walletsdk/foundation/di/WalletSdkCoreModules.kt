package com.michaeltchuang.walletsdk.foundation.di

import com.michaeltchuang.walletsdk.account.di.accountModules
import com.michaeltchuang.walletsdk.deeplink.di.deepLinkModules
import com.michaeltchuang.walletsdk.transaction.di.transactionModules

val walletSdkCoreModules =
    buildList {
        addAll(foundationModules)
        addAll(deepLinkModules)
        addAll(accountModules)
        addAll(deepLinkModules)
        addAll(transactionModules)
    }
