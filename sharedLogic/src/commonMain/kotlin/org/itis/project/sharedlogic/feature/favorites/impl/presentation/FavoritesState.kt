package org.itis.project.sharedlogic.feature.favorites.impl.presentation

import org.itis.project.sharedlogic.core.domain.model.FavoriteApod

data class FavoritesState(
    val isLoading: Boolean = false,
    val favorites: List<FavoriteApod> = emptyList(),
    val error: String? = null
)

sealed class FavoritesEvent {
    data object LoadFavorites : FavoritesEvent()
    data class RemoveFavorite(val date: String) : FavoritesEvent()
    data object ClearError : FavoritesEvent()
}