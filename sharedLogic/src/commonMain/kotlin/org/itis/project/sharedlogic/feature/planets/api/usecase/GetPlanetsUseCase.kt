package org.itis.project.sharedlogic.feature.planets.api.usecase

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetModel

interface GetPlanetsUseCase {
    suspend operator fun invoke(): List<PlanetModel>
}