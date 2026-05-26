package org.itis.project.sharedlogic.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.itis.project.sharedlogic.config.BuildConfig
import org.itis.project.sharedlogic.network.pojo.response.ApodResponse

class NasaApi(private val client: HttpClient) {

    suspend fun getApod(date: String? = null): ApodResponse =
        client.get("https://api.nasa.gov/planetary/apod") {
            parameter("api_key", BuildConfig.NASA_API_KEY)
            date?.let { parameter("date", it) }
        }.body()
}