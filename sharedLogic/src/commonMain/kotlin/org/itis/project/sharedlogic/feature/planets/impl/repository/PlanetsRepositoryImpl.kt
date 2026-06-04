package org.itis.project.sharedlogic.feature.planets.impl.repository

import org.itis.project.sharedlogic.core.data.PlanetAssets
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetSummaryModel
import org.itis.project.sharedlogic.feature.planets.api.repository.PlanetsRepository


internal class PlanetsRepositoryImpl : PlanetsRepository {

    override suspend fun getAll(): List<PlanetSummaryModel> {
        return PlanetAssets.all()
    }

    override suspend fun getDetail(id: String): PlanetDetailModel {
        return PlanetAssets.detail(id) ?: error("Planet not found")
    }
}