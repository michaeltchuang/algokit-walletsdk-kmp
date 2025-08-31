package com.michaeltchuang.walletsdk.utils


private const val REGISTER_EVENT_KEY = "register" // event type
private const val REGISTER_ACCOUNT_TYPE_KEY = "type" // param

// TODO use Account.Type if possible instead of CreationType
enum class CreationType(val analyticsValue: String) {
    LEDGER("ledger"),
    RECOVER("recover"),
    CREATE("create"),
    REKEYED("rekeyed"),
    WATCH("watch")
}
