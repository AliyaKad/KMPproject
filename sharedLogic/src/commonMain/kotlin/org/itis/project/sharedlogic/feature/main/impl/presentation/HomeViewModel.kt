package org.itis.project.sharedlogic.feature.main.impl.presentation

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.analytics.AnalyticsViewModel
import org.itis.project.sharedlogic.core.data.ErrorContext
import org.itis.project.sharedlogic.core.data.Errors
import org.itis.project.sharedlogic.core.data.PlanetOfDay
import org.itis.project.sharedlogic.core.viewmodel.BaseViewModel
import org.itis.project.sharedlogic.feature.main.api.usecase.GetApodUseCase
import org.itis.project.sharedlogic.feature.main.api.usecase.GetIssPositionUseCase
import org.itis.project.sharedlogic.feature.main.api.usecase.GetGreetingUseCase
import org.itis.project.sharedlogic.feature.planets.api.usecase.GetPlanetOfDayUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar



class HomeViewModel : AnalyticsViewModel<HomeState, HomeEvent, HomeEffect>(
    initialState = HomeState()
) {

    private val getApodUseCase: GetApodUseCase by inject()
    private val getIssPositionUseCase: GetIssPositionUseCase by inject()
    private val getPlanetOfDayUseCase: GetPlanetOfDayUseCase by inject()
    private val getGreetingUseCase: GetGreetingUseCase by inject()

    init {
        obtainIntent(HomeEvent.Load)
    }

    fun onScreenOpen() {
        logScreenOpen("home")
    }

    override fun obtainIntent(intent: HomeEvent) {
        when (intent) {
            HomeEvent.Load -> load()
            HomeEvent.Refresh -> refresh()
        }
    }

    private fun load() {
        updateState {
            it.copy(
                apodLoading = true,
                issLoading = true,
                apodError = null,
                issError = null
            )
        }

        loadGreeting()
        loadPlanetOfDay()
        loadApod()
        loadIss()
    }

    private fun refresh() {
        load()
    }

    private fun loadGreeting() {
        viewModelScope.launch {
            runCatching { getGreetingUseCase.invoke() }
                .onSuccess { greeting ->
                    updateState { it.copy(greeting = greeting) }
                }
                .onFailure {
                    updateState { it.copy(greeting = "Привет, исследователь космоса") }
                }
        }
    }

    private fun loadApod() {
        viewModelScope.launch {
            runCatching { getApodUseCase.invoke() }
                .onSuccess { apod ->
                    updateState { it.copy(apod = apod, apodLoading = false, apodError = null) }
                }
                .onFailure { e ->
                    updateState { it.copy(apodLoading = false, apodError = Errors.friendly(e, ErrorContext.Nasa)) }
                    logEvent("error_occurred", mapOf(
                        "error_message" to (e.message ?: "unknown"),
                        "context" to "apod_load"
                    ))
                }
        }
    }

    private fun loadIss() {
        viewModelScope.launch {
            runCatching { getIssPositionUseCase.invoke() }
                .onSuccess { pos ->
                    updateState { it.copy(issPosition = pos, issLoading = false, issError = null) }
                }
                .onFailure { e ->
                    updateState { it.copy(issLoading = false, issError = Errors.friendly(e, ErrorContext.Iss)) }
                    logEvent("error_occurred", mapOf(
                        "error_message" to (e.message ?: "unknown"),
                        "context" to "iss_load"
                    ))
                }
        }
    }

    private fun loadPlanetOfDay() {
        viewModelScope.launch {
            runCatching { getPlanetOfDayUseCase.invoke() }
                .onSuccess { (planetName, planetFact) ->
                    updateState { it.copy(planetName = planetName, planetFact = planetFact) }
                }
                .onFailure {
                    updateState { it.copy(planetName = "Земля", planetFact = "Единственная планета, на которой подтверждена жизнь.") }
                }
        }
    }
}