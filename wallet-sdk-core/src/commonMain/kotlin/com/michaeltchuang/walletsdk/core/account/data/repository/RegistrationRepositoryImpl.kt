/*
package com.michaeltchuang.walletsdk.core.account.data.repository

import com.michaeltchuang.walletsdk.core.account.data.sharedpref.RegistrationSkipLocalSource
import com.michaeltchuang.walletsdk.core.account.domain.repository.core.RegistrationRepository

class RegistrationRepositoryImpl(
    private val registrationSkipLocalSource: RegistrationSkipLocalSource
) : RegistrationRepository {

    override fun setRegistrationSkipPreferenceAsSkipped() {
        registrationSkipLocalSource.saveData(true)
    }

    override fun getRegistrationSkipped(): Boolean {
        return registrationSkipLocalSource.getData(RegistrationSkipLocalSource.Companion.defaultRegisterSkipPreference)
    }
}*/
