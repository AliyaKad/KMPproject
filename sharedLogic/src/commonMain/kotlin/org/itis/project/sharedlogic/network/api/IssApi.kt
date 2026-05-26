package org.itis.project.sharedlogic.network.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.itis.project.sharedlogic.network.pojo.response.IssNowResponse

class IssApi(private val client: HttpClient) {
    suspend fun getIssNow(): IssNowResponse =
        client.get("http://api.open-notify.org/iss-now.json").body()
}