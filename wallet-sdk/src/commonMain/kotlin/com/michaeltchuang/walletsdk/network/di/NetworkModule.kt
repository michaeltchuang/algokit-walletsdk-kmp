package com.michaeltchuang.walletsdk.network.di

import com.michaeltchuang.walletsdk.network.service.AccountInformationApiService
import com.michaeltchuang.walletsdk.network.service.AccountInformationApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule =
    module {

        // Provide HttpClient with JSON serialization and logging
        single<HttpClient> {
            HttpClient {
                install(ContentNegotiation) {
                    json(
                        Json {
                            ignoreUnknownKeys = true
                            isLenient = true
                            coerceInputValues = true
                        },
                    )
                }

                install(Logging) {
                    logger =
                        object : Logger {
                            override fun log(message: String) {
                                println("HTTP Client: $message")
                            }
                        }
                    level = LogLevel.ALL
                }
            }
        }

        // Provide AccountInformationApiService
        single<AccountInformationApiService> {
            AccountInformationApiServiceImpl(
                httpClient = get()
            )
        }
    }
