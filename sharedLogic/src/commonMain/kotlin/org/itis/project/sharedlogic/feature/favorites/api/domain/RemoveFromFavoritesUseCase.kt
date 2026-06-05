package org.itis.project.sharedlogic.feature.favorites.api.domain

interface RemoveFromFavoritesUseCase {
    suspend operator fun invoke(date: String)
}