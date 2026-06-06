package org.itis.project.sharedlogic.feature.favorites.api.data

import org.itis.project.sharedlogic.core.domain.model.FavoriteApod


interface FavoritesRepository {
    suspend fun addToFavorites(apod: FavoriteApod)
    suspend fun removeFromFavorites(date: String)
    suspend fun getAllFavorites(): List<FavoriteApod>
    suspend fun isFavorite(date: String): Boolean
    suspend fun getFavoritesCount(): Long
}