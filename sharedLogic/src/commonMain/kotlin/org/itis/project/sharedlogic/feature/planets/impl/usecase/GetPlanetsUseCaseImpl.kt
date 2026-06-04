package org.itis.project.sharedlogic.feature.planets.impl.usecase

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetModel
import org.itis.project.sharedlogic.feature.planets.api.repository.PlanetsRepository
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetsUseCase

internal class GetPlanetsUseCaseImpl(
    private val repository: PlanetsRepository
) : GetPlanetsUseCase {
    override suspend fun invoke(): List<PlanetModel> = repository.getAll()
}