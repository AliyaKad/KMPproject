package org.itis.project.sharedlogic.feature.planets.api.repository

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetSummaryModel

interface PlanetsRepository {
    suspend fun getAll(): List<PlanetSummaryModel>
    suspend fun getDetail(id: String): PlanetDetailModel
}