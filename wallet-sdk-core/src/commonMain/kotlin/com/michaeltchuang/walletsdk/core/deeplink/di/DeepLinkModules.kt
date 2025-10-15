package com.michaeltchuang.walletsdk.core.deeplink.di

import com.michaeltchuang.walletsdk.core.deeplink.DeeplinkHandler
import com.michaeltchuang.walletsdk.core.deeplink.builder.KeyRegTransactionDeepLinkBuilder
import com.michaeltchuang.walletsdk.core.deeplink.builder.MnemonicDeepLinkBuilder
import com.michaeltchuang.walletsdk.core.deeplink.parser.CreateDeepLink
import com.michaeltchuang.walletsdk.core.deeplink.parser.CreateDeepLinkImpl
import com.michaeltchuang.walletsdk.core.deeplink.parser.ParseDeepLinkPayload
import com.michaeltchuang.walletsdk.core.deeplink.parser.ParseDeepLinkPayloadImpl
import com.michaeltchuang.walletsdk.core.deeplink.parser.PeraUriParser
import com.michaeltchuang.walletsdk.core.deeplink.parser.PeraUriParserImpl
import com.michaeltchuang.walletsdk.core.deeplink.parser.query.MnemonicQueryParser
import org.koin.dsl.module

val deepLinkModules =
    listOf(
        module {

            // Provide PeraUriParser
            single<PeraUriParser> { PeraUriParserImpl() }

            // Provide ParseDeepLinkPayload
            single<ParseDeepLinkPayload> {
                ParseDeepLinkPayloadImpl(
                    peraUriParser = get(),
                    mnemonicQueryParser = MnemonicQueryParser(get()),
                )
            }

            // Provide CreateDeepLink
            single<CreateDeepLink> {
                CreateDeepLinkImpl(
                    parseDeepLinkPayload = get(),
                    mnemonicDeepLinkBuilder = MnemonicDeepLinkBuilder(),
                    keyRegTransactionDeepLinkBuilder = KeyRegTransactionDeepLinkBuilder(),
                )
            }

            single { DeeplinkHandler(get()) }
        },
    )
