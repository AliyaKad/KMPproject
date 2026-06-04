package org.itis.project.sharedlogic.feature.planets.impl.presentation

import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.core.viewmodel.BaseViewModel
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetDetailUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject


class PlanetDetailViewModel : BaseViewModel<PlanetDetailState, PlanetDetailIntent, Nothing>
    (PlanetDetailState()
), KoinComponent {

    private val getPlanetDetailUseCase: GetPlanetDetailUseCase by inject()

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
                }
        }
    }
}