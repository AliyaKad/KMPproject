package org.itis.project.sharedlogic.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.SIMPLE
import org.itis.project.sharedlogic.network.HttpEngineFactory
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpEngineFactory()
    }

    single<Json> {
        Json {
            isLenient = true
            ignoreUnknownKeys = true
        }
    }

    single {
        createHttpClient(
            engine = get<HttpEngineFactory>().createEngine(),
            json = get(),
        )
    }
}


fun createHttpClient(
    engine: HttpClientEngineFactory<HttpClientEngineConfig>,
    json: Json,
) = HttpClient(engine) {
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.BODY
    }
    install(ContentNegotiation) {
        json(json)
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 5000
        requestTimeoutMillis = 30000
        socketTimeoutMillis = 10000
    }
}