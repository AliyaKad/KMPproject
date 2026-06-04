package org.itis.project.sharedlogic.feature.main.impl.presentation

import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.core.viewmodel.BaseViewModel
import org.itis.project.sharedlogic.feature.apod.api.usecase.GetApodByDateUseCase
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetailEffect as Effect
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetailEvent as Event
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetailState as State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ApodDetailViewModel : BaseViewModel<State, Event, Effect>(
    initialState = State()
), KoinComponent {

    private val getApodByDateUseCase: GetApodByDateUseCase by inject()

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