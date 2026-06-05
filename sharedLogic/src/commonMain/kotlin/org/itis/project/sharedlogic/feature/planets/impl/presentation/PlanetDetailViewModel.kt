package org.itis.project.sharedlogic.feature.planets.impl.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.analytics.AnalyticsViewModel
import org.itis.project.sharedlogic.core.viewmodel.BaseViewModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetDetailUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PlanetDetailViewModel : AnalyticsViewModel<PlanetDetailState, PlanetDetailIntent, Nothing>(
    PlanetDetailState()
) {

    private val getPlanetDetailUseCase: GetPlanetDetailUseCase by inject()

    fun onScreenOpen(planetId: String) {
        logScreenOpen("planet_detail")
        obtainIntent(PlanetDetailIntent.Load(planetId))
    }

    override fun obtainIntent(intent: PlanetDetailIntent) {
        when (intent) {
            is PlanetDetailIntent.Load -> load(intent.id)
            else -> {}
        }
    }

    private fun load(id: String) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            runCatching { getPlanetDetailUseCase(id) }
                .onSuccess { detail ->
                    updateState { it.copy(isLoading = false, detail = detail, error = null) }
                }
                .onFailure { e ->
                    updateState { it.copy(isLoading = false, error = e.message) }
                    logEvent("error_occurred", mapOf(
                        "error_message" to (e.message ?: "unknown"),
                        "context" to "planet_detail_load_$id"
                    ))
                }
        }
    }
}