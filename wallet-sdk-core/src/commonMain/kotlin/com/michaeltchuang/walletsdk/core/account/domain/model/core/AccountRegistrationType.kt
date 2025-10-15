package com.michaeltchuang.walletsdk.core.account.domain.model.core

sealed interface AccountRegistrationType {
    data object Algo25 : AccountRegistrationType

    data object LedgerBle : AccountRegistrationType

    data object NoAuth : AccountRegistrationType

    data object HdKey : AccountRegistrationType

    data object Falcon24 : AccountRegistrationType
}
