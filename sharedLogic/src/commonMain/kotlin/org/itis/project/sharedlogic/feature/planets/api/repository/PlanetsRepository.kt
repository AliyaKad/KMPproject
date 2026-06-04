package org.itis.project.sharedlogic.feature.planets.api.repository

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetModel

interface PlanetsRepository {
    suspend fun getAll(): List<PlanetModel>
    suspend fun getDetail(id: String): PlanetDetailModel
}