package org.itis.project.sharedlogic.feature.planets.api.usecase

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel

interface GetPlanetDetailUseCase {
    suspend operator fun invoke(id: String): PlanetDetailModel
}