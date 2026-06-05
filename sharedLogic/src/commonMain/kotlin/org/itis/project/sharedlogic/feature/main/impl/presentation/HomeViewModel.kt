package org.itis.project.sharedlogic.presentation.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.analytics.AnalyticsViewModel
import org.itis.project.sharedlogic.core.data.ErrorContext
import org.itis.project.sharedlogic.core.data.Errors
import org.itis.project.sharedlogic.core.data.PlanetOfDay
import org.itis.project.sharedlogic.core.viewmodel.BaseViewModel
import org.itis.project.sharedlogic.feature.main.api.usecase.GetApodUseCase
import org.itis.project.sharedlogic.feature.main.api.usecase.GetIssPositionUseCase
import org.itis.project.sharedlogic.feature.main.impl.presentation.HomeEffect
import org.itis.project.sharedlogic.feature.main.impl.presentation.HomeEvent
import org.itis.project.sharedlogic.feature.main.impl.presentation.HomeState
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.Calendar



class HomeViewModel : AnalyticsViewModel<HomeState, HomeEvent, HomeEffect>(
    initialState = HomeState()
) {

    private val getApodUseCase: GetApodUseCase by inject()
    private val getIssPositionUseCase: GetIssPositionUseCase by inject()

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
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 5..11 -> "Доброе утро, путник"
            in 12..17 -> "Привет, исследователь"
            in 18..22 -> "Добрый вечер, наблюдатель"
            else -> "Ночь — лучшее время для звёзд"
        }
        val (planetName, planetFact) = PlanetOfDay.getPlanetOfDay()

        setState {
            it.copy(
                greeting = greeting,
                planetName = planetName,
                planetFact = planetFact,
                apodLoading = true,
                issLoading = true,
                apodError = null,
                issError = null
            )
        }

        loadApod()
        loadIss()
    }

    private fun refresh() {
        load()
    }

    private fun loadApod() {
        viewModelScope.launch {
            runCatching { getApodUseCase.invoke() }
                .onSuccess { apod ->
                    setState { it.copy(apod = apod, apodLoading = false, apodError = null) }
                }
                .onFailure { e ->
                    setState { it.copy(apodLoading = false, apodError = Errors.friendly(e, ErrorContext.Nasa)) }
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
                    setState { it.copy(issPosition = pos, issLoading = false, issError = null) }
                }
                .onFailure { e ->
                    setState { it.copy(issLoading = false, issError = Errors.friendly(e, ErrorContext.Iss)) }
                    logEvent("error_occurred", mapOf(
                        "error_message" to (e.message ?: "unknown"),
                        "context" to "iss_load"
                    ))
                }
        }
    }
}