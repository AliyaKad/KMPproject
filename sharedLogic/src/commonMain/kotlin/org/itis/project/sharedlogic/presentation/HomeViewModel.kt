package org.itis.project.sharedlogic.presentation.home

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.data.ErrorContext
import org.itis.project.sharedlogic.data.Errors
import org.itis.project.sharedlogic.data.IssRepository
import org.itis.project.sharedlogic.data.NasaRepository
import org.itis.project.sharedlogic.data.PlanetOfDay
import org.itis.project.sharedlogic.domain.Apod
import org.itis.project.sharedlogic.domain.IssPosition
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class HomeState(
    val greeting: String = "",
    val apod: Apod? = null,
    val apodLoading: Boolean = true,
    val apodError: String? = null,
    val planetName: String = "",
    val planetFact: String = "",
    val issPosition: IssPosition? = null,
    val issLoading: Boolean = true,
    val issError: String? = null
)

class HomeViewModel(
    private val nasaRepo: NasaRepository,
    private val issRepo: IssRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        load()
    }

    fun refresh() {
        load()
    }

    private fun load() {
        val greeting = "Привет, исследователь космоса"

        val (planetName, planetFact) = PlanetOfDay.getPlanetOfDay()

        _state.value = _state.value.copy(
            greeting = greeting,
            planetName = planetName,
            planetFact = planetFact,
            apodLoading = true,
            issLoading = true
        )

        loadApod()
        loadIss()
    }

    private fun loadApod() {
        viewModelScope.launch {
            runCatching { nasaRepo.getApod() }
                .onSuccess { apod ->
                    _state.value = _state.value.copy(apod = apod, apodLoading = false, apodError = null)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(apodLoading = false, apodError = Errors.friendly(e, ErrorContext.Nasa))
                }
        }
    }

    private fun loadIss() {
        viewModelScope.launch {
            runCatching { issRepo.getIssPosition() }
                .onSuccess { pos ->
                    _state.value = _state.value.copy(issPosition = pos, issLoading = false, issError = null)
                }
                .onFailure { e ->
                    _state.value = _state.value.copy(issLoading = false, issError = Errors.friendly(e, ErrorContext.Iss))
                }
        }
    }
}