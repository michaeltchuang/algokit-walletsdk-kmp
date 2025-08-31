package com.michaeltchuang.walletsdk.deeplink.di

import com.michaeltchuang.walletsdk.deeplink.DeeplinkHandler
import com.michaeltchuang.walletsdk.deeplink.builder.KeyRegTransactionDeepLinkBuilder
import com.michaeltchuang.walletsdk.deeplink.builder.MnemonicDeepLinkBuilder
import com.michaeltchuang.walletsdk.deeplink.parser.CreateDeepLink
import com.michaeltchuang.walletsdk.deeplink.parser.CreateDeepLinkImpl
import com.michaeltchuang.walletsdk.deeplink.parser.ParseDeepLinkPayload
import com.michaeltchuang.walletsdk.deeplink.parser.ParseDeepLinkPayloadImpl
import com.michaeltchuang.walletsdk.deeplink.parser.PeraUriParser
import com.michaeltchuang.walletsdk.deeplink.parser.PeraUriParserImpl
import com.michaeltchuang.walletsdk.deeplink.parser.query.MnemonicQueryParser
import org.koin.dsl.module

val deepLinkModule = module {

    // Provide PeraUriParser
    single<PeraUriParser> { PeraUriParserImpl() }

    // Provide ParseDeepLinkPayload
/*    single<ParseDeepLinkPayload> {
        ParseDeepLinkPayloadImpl(
            peraUriParser = get(),
            mnemonicQueryParser = MnemonicQueryParser(get()),
        )
    }*/

    // Provide CreateDeepLink
    single<CreateDeepLink> {
        CreateDeepLinkImpl(
            parseDeepLinkPayload = get(),
            mnemonicDeepLinkBuilder = MnemonicDeepLinkBuilder(),
            keyRegTransactionDeepLinkBuilder = KeyRegTransactionDeepLinkBuilder()
        )
    }

    single { DeeplinkHandler(get()) }
}
