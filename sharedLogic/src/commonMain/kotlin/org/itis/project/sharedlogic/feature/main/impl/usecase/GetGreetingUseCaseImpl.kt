package org.itis.project.sharedlogic.feature.main.impl.usecase

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.itis.project.sharedlogic.feature.main.api.usecase.GetGreetingUseCase
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

internal class GetGreetingUseCaseImpl : GetGreetingUseCase {
    @OptIn(ExperimentalTime::class)
    override suspend fun invoke(): String {
        val now = Clock.System.now()
        val hour = now.toLocalDateTime(TimeZone.Companion.currentSystemDefault()).hour
        return when (hour) {
            in 5..11 -> "Доброе утро, путник"
            in 12..17 -> "Привет, исследователь"
            in 18..22 -> "Добрый вечер, наблюдатель"
            else -> "Ночь — лучшее время для звёзд"
        }
    }
}