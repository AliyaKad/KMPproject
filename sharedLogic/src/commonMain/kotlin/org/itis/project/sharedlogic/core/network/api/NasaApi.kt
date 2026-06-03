package org.itis.project.sharedlogic.core.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.itis.project.sharedlogic.core.network.pojo.response.ApodResponse
import org.itis.project.sharedlogic.platform

class NasaApi(private val client: HttpClient) {

    suspend fun getApod(date: String? = null): ApodResponse =
        client.get(getBaseUrl()) {
            date?.let { parameter("date", it) }
        }.body()

    private fun getBaseUrl(): String {
        return if (platform() == "Android") {
            "http://10.0.2.2:5000/v1/apod/"
        } else {
            "http://localhost:5000/v1/apod/"
        }
    }
}