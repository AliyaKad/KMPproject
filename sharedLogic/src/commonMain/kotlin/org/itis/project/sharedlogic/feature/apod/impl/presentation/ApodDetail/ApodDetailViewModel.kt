package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail

import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.feature.apod.api.usecase.GetApodByDateUseCase
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailEffect as Effect
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailEvent as Event
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailState as State
import org.koin.core.component.inject
import org.itis.project.sharedlogic.analytics.AnalyticsViewModel
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailState


class ApodDetailViewModel : AnalyticsViewModel<State, Event, Effect>(
    initialState = ApodDetailState()
) {

    private val getApodByDateUseCase: GetApodByDateUseCase by inject()

    fun onScreenOpen(date: String) {
        logScreenOpen("apod_detail")
        obtainIntent(Event.Load(date))
    }

    override fun obtainIntent(intent: Event) {
        when (intent) {
            is Event.Load -> load(intent.date)
            Event.ToggleFavorite -> toggleFavorite()
            Event.Share -> share()
        }
    }

    private fun load(date: String) {
        updateState { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            runCatching { getApodByDateUseCase(date) }
                .onSuccess { apod ->
                    updateState { it.copy(apod = apod, isLoading = false, error = null) }
                }
                .onFailure { e ->
                    updateState { it.copy(isLoading = false, error = e.message) }
                    logEvent("error_occurred", mapOf(
                        "error_message" to (e.message ?: "unknown"),
                        "context" to "apod_detail_load_${date}"
                    ))
                }
        }
    }

    private fun toggleFavorite() {
        val newFavorite = !currentState().isFavorite
        updateState { it.copy(isFavorite = newFavorite) }
        emitEffect(Effect.FavoriteToggled(newFavorite))
    }

    private fun share() {
        currentState().apod?.let { apod ->
            emitEffect(Effect.ShareRequested(apod.url))
        }
    }
}