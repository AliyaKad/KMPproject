package org.itis.project.sharedlogic.feature.planets.api.usecase

interface GetPlanetOfDayUseCase {
    suspend operator fun invoke(): Pair<String, String>
}