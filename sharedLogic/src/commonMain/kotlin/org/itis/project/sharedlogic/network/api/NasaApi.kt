package org.itis.project.sharedlogic.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.itis.project.sharedlogic.config.BuildConfig
import org.itis.project.sharedlogic.network.pojo.response.ApodResponse

class NasaApi(private val client: HttpClient) {

    suspend fun getApod(date: String? = null): ApodResponse =
        client.get("http://localhost:5000/v1/apod/") {
            date?.let { parameter("date", it) }
        }.body()
}