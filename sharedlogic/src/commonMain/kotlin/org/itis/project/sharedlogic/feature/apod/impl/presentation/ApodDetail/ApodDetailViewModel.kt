package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail

import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.core.domain.model.FavoriteApod
import org.itis.project.sharedlogic.feature.apod.api.usecase.GetApodByDateUseCase
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailEffect as Effect
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailEvent as Event
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailState as State
import org.koin.core.component.inject
import org.itis.project.sharedlogic.analytics.AnalyticsViewModel
import org.itis.project.sharedlogic.feature.favorites.api.domain.AddToFavoritesUseCase
import org.itis.project.sharedlogic.feature.favorites.api.domain.IsFavoriteUseCase
import org.itis.project.sharedlogic.feature.favorites.api.domain.RemoveFromFavoritesUseCase
import org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail.ApodDetailState

class ApodDetailViewModel : AnalyticsViewModel<State, Event, Effect>(
    initialState = ApodDetailState()
) {

    private val getApodByDateUseCase: GetApodByDateUseCase by inject()
    private val addToFavoritesUseCase: AddToFavoritesUseCase by inject()
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase by inject()
    private val isFavoriteUseCase: IsFavoriteUseCase by inject()

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
            runCatching { isFavoriteUseCase(date) }
                .onSuccess { isFavorite ->
                    updateState { it.copy(isFavorite = isFavorite) }
                }
                .onFailure { e ->
                    logEvent("error_checking_favorite", mapOf(
                        "error_message" to (e.message ?: "unknown"),
                        "date" to date
                    ))
                }

            runCatching { getApodByDateUseCase(date) }
                .onSuccess { apod ->
                    updateState {
                        it.copy(
                            apod = apod,
                            isLoading = false,
                            error = null
                        )
                    }
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
        val currentApod = currentState().apod
        if (currentApod == null) {
            logEvent("toggle_favorite_error", mapOf("reason" to "apod_is_null"))
            return
        }

        val willBeFavorite = !currentState().isFavorite

        updateState { it.copy(isFavorite = willBeFavorite) }

        viewModelScope.launch {
            runCatching {
                if (willBeFavorite) {
                    val favoriteApod = FavoriteApod(
                        date = currentApod.date,
                        title = currentApod.title,
                        explanation = currentApod.explanation,
                        url = currentApod.url,
                        hdurl = currentApod.hdurl,
                        mediaType = currentApod.mediaType,
                        copyright = currentApod.copyright,
                        addedAt = System.currentTimeMillis()
                    )
                    addToFavoritesUseCase(favoriteApod)
                    emitEffect(Effect.FavoriteToggled(true))
                    logEvent("favorite_added", mapOf("date" to currentApod.date))
                } else {
                    removeFromFavoritesUseCase(currentApod.date)
                    emitEffect(Effect.FavoriteToggled(false))
                    logEvent("favorite_removed", mapOf("date" to currentApod.date))
                }
            }.onFailure { e ->
                updateState { it.copy(isFavorite = !willBeFavorite) }
                emitEffect(Effect.ShowError(e.message ?: "Ошибка при сохранении в избранное"))
                logEvent("toggle_favorite_error", mapOf(
                    "error_message" to (e.message ?: "unknown"),
                    "action" to if (willBeFavorite) "add" else "remove"
                ))
            }
        }
    }

    private fun share() {
        currentState().apod?.let { apod ->
            emitEffect(Effect.ShareRequested(apod.url))
        }
    }
}