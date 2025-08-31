package com.michaeltchuang.walletsdk.account.domain.model.core

sealed interface OnboardingAccountType {

    val wordCount: Int

    @Suppress("MagicNumber")
    data object Algo25 : OnboardingAccountType {
        override val wordCount: Int = 25
    }

    @Suppress("MagicNumber")
    data object HdKey : OnboardingAccountType {
        override val wordCount: Int = 24
    }
}
