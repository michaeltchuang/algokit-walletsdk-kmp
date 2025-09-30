package com.michaeltchuang.walletsdk.network.di

import com.michaeltchuang.walletsdk.network.service.AccountInformationApiService
import com.michaeltchuang.walletsdk.network.service.AccountInformationApiServiceImpl
import com.michaeltchuang.walletsdk.settings.domain.provideNodePreferenceRepository
import com.michaeltchuang.walletsdk.settings.presentation.screens.AlgorandNetwork
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val networkModule = module {

    // Provide HttpClient with JSON serialization and logging
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    coerceInputValues = true
                })
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println("HTTP Client: $message")
                    }
                }
                level = LogLevel.INFO
            }
        }
    }

    // Provide base URL provider function
    single<suspend () -> String> {
        val nodeRepository = provideNodePreferenceRepository()
        return@single {
            try {
                nodeRepository.getSavedNodePreferenceFlow().first().baseUrl
            } catch (e: Exception) {
                // Fallback to TestNet if there's any issue
                AlgorandNetwork.TESTNET.baseUrl
            }
        }
    }

    // Provide AccountInformationApiService
    single<AccountInformationApiService> {
        AccountInformationApiServiceImpl(
            httpClient = get(),
            baseUrlProvider = get()
        )
    }
}