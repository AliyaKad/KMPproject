package org.itis.project.sharedlogic.core.network.api

import io.ktor.client.request.get
import org.itis.project.sharedlogic.core.network.pojo.response.PlanetsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.parameter
import org.itis.project.sharedlogic.core.network.pojo.response.PlanetDetailResponse


class SolarApi(private val client: HttpClient) {

    private val base = "https://api.le-systeme-solaire.net/rest/bodies"

    suspend fun listPlanets(): PlanetsResponse =
        client.get(base) {
            parameter("filter[]", "isPlanet,eq,true")
        }.body()

    suspend fun getPlanet(id: String): PlanetDetailResponse =
        client.get("$base/$id").body()
}
