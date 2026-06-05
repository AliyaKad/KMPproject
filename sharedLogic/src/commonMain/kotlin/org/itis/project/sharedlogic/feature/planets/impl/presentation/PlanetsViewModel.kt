package org.itis.project.sharedlogic.feature.planets.impl.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.analytics.AnalyticsViewModel
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetsUseCase
import org.koin.core.component.inject

class PlanetsViewModel : AnalyticsViewModel<PlanetsState, PlanetsIntent, Nothing>(
    PlanetsState()
) {

    private val getPlanetsUseCase: GetPlanetsUseCase by inject()

    init {
        obtainIntent(PlanetsIntent.Load)
    }

    fun onScreenOpen() {
        logScreenOpen("planets_list")
    }

    fun logPlanetClick(planetId: String, planetName: String) {
        logEvent("planet_click", mapOf(
            "planet_id" to planetId,
            "planet_name" to planetName
        ))
    }

    override fun obtainIntent(intent: PlanetsIntent) {
        when (intent) {
            PlanetsIntent.Load -> load()
            is PlanetsIntent.Search -> {
                setState { it.copy(query = intent.query) }
                if (intent.query.isNotBlank()) {
                    logEvent("search_planets", mapOf("query" to intent.query))
                }
            }
        }
    }

    private fun load() {
        setState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            runCatching { getPlanetsUseCase() }
                .onSuccess { planets ->
                    setState { it.copy(isLoading = false, planets = planets, error = null) }
                    logEvent("planets_loaded", mapOf("count" to planets.size.toString()))
                }
                .onFailure { e ->
                    setState { it.copy(isLoading = false, error = e.message) }
                    logEvent("planets_load_error", mapOf("error" to (e.message ?: "unknown")))
                }
        }
    }
}