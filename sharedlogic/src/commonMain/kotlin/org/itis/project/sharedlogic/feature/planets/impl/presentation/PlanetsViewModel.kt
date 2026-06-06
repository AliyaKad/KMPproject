package org.itis.project.sharedlogic.feature.planets.impl.presentation

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

    override fun obtainIntent(intent: PlanetsIntent) {
        when (intent) {
            PlanetsIntent.Load -> load()
            is PlanetsIntent.Search -> updateState { it.copy(query = intent.query) }
        }
    }

    private fun load() {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            runCatching { getPlanetsUseCase() }
                .onSuccess { planets ->
                    updateState { it.copy(isLoading = false, planets = planets, error = null) }
                }
                .onFailure { e ->
                    updateState { it.copy(isLoading = false, error = e.message) }
                }
        }
    }
}