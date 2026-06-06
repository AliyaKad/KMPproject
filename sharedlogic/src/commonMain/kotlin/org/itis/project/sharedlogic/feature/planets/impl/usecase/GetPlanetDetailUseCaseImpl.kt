package org.itis.project.sharedlogic.feature.planets.impl.usecase

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.repository.PlanetsRepository
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetDetailUseCase

internal class GetPlanetDetailUseCaseImpl(
    private val repository: PlanetsRepository
) : GetPlanetDetailUseCase {
    override suspend fun invoke(id: String): PlanetDetailModel = repository.getDetail(id)
}