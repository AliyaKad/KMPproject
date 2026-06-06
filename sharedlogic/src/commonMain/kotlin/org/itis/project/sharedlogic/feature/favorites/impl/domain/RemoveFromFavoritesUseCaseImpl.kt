package org.itis.project.sharedlogic.feature.favorites.impl.domain

import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesRepository
import org.itis.project.sharedlogic.feature.favorites.api.domain.RemoveFromFavoritesUseCase

class RemoveFromFavoritesUseCaseImpl(
    private val favoritesRepository: FavoritesRepository
) : RemoveFromFavoritesUseCase {
    override suspend operator fun invoke(date: String) {
        favoritesRepository.removeFromFavorites(date)
    }
}