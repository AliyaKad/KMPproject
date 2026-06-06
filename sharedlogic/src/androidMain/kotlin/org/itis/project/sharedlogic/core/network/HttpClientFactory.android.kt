package org.itis.project.sharedlogic.core.network

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.config
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber

actual open class HttpEngineFactory actual constructor() {
    actual fun createEngine(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp.config {
        config {
            retryOnConnectionFailure(true)
        }
        addInterceptor(
            HttpLoggingInterceptor { Timber.tag("Network").d(it) }.apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }
        )
    }
}