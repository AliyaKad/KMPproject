package org.itis.project.sharedlogic.feature.planets.impl.usecase

import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.itis.project.sharedlogic.core.data.PlanetAssets
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetOfDayUseCase
import kotlin.time.ExperimentalTime

class GetPlanetOfDayUseCaseImpl : GetPlanetOfDayUseCase {

    @OptIn(ExperimentalTime::class)
    override suspend fun invoke(): Pair<String, String> {
        val tz = TimeZone.currentSystemDefault()
        val now = Clock.System.now().toLocalDateTime(tz)
        val date = now.date
        val seed = date.toEpochDays()
        return PlanetAssets.planetOfTheDay(seed)
    }
}