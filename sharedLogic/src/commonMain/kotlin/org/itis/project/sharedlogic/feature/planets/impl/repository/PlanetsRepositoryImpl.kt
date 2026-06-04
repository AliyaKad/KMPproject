package org.itis.project.sharedlogic.feature.planets.impl.repository

import org.itis.project.sharedlogic.core.network.api.SolarApi
import org.itis.project.sharedlogic.core.network.mapper.mapToDetail
import org.itis.project.sharedlogic.core.network.mapper.mapToModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetModel
import org.itis.project.sharedlogic.feature.planets.api.repository.PlanetsRepository


internal class PlanetsRepositoryImpl(
    private val api: SolarApi
) : PlanetsRepository {

    override suspend fun getAll(): List<PlanetModel> {
        val response = api.listPlanets()
        return response.bodies?.filter { it.isPlanet == true }?.map { it.mapToModel() } ?: emptyList()
    }

    override suspend fun getDetail(id: String): PlanetDetailModel {
        val planet = api.getPlanet(id).mapToDetail()
        return planet
    }
}