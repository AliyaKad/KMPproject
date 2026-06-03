package org.itis.project.sharedlogic.network

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.config
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.logging.HttpLoggingInterceptor
import java.util.logging.Logger

actual open class HttpEngineFactory actual constructor() {
    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp.config {
        config {
            retryOnConnectionFailure(true)
        }
        addInterceptor(
            HttpLoggingInterceptor { message ->
                Logger.getGlobal().info("[Network] $message")
            }.apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        )
    }
}