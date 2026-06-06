package org.itis.project.sharedlogic.feature.favorites.impl.domain

import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesRepository
import org.itis.project.sharedlogic.feature.favorites.api.domain.IsFavoriteUseCase

class IsFavoriteUseCaseImpl(
    private val favoritesRepository: FavoritesRepository
) : IsFavoriteUseCase {
    override suspend operator fun invoke(date: String): Boolean {
        return favoritesRepository.isFavorite(date)
    }
}