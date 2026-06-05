package org.itis.project.sharedlogic.feature.favorites.impl.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.itis.project.sharedlogic.analytics.AnalyticsService
import org.itis.project.sharedlogic.feature.favorites.api.domain.GetFavoritesUseCase
import org.itis.project.sharedlogic.feature.favorites.api.domain.RemoveFromFavoritesUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class FavoritesViewModel(
    private val getFavoritesUseCase: GetFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel(), KoinComponent {

    private val analyticsService: AnalyticsService by inject()

    private val _state = MutableStateFlow(FavoritesState())
    val state: StateFlow<FavoritesState> = _state.asStateFlow()

    fun onScreenOpen() {
        analyticsService.logEvent("screen_open", mapOf("screen_name" to "favorites"))
        loadFavorites()
    }

    fun handleEvent(event: FavoritesEvent) {
        when (event) {
            FavoritesEvent.LoadFavorites -> loadFavorites()
            is FavoritesEvent.RemoveFavorite -> removeFavorite(event.date)
            FavoritesEvent.ClearError -> clearError()
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            try {
                val favorites = getFavoritesUseCase()
                _state.value = _state.value.copy(
                    isLoading = false,
                    favorites = favorites,
                    error = null
                )
                analyticsService.logEvent("favorites_loaded", mapOf("count" to favorites.size.toString()))
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Ошибка загрузки избранного"
                )
                analyticsService.logEvent("error_occurred", mapOf(
                    "error_message" to (e.message ?: "unknown"),
                    "context" to "favorites_load"
                ))
            }
        }
    }

    private fun removeFavorite(date: String) {
        viewModelScope.launch {
            try {
                removeFromFavoritesUseCase(date)
                analyticsService.logEvent("favorite_removed", mapOf("date" to date))
                loadFavorites() // Обновляем список
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = e.message ?: "Ошибка удаления из избранного"
                )
            }
        }
    }

    private fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}