package com.michaeltchuang.walletsdk.transaction.di

val transactionModule =
    listOf(
        keyRegTransactionModule,
        pendingTransactionRequestViewModelModule,
    )
