package org.itis.project.sharedlogic.feature.favorites.impl.domain

import org.itis.project.sharedlogic.core.domain.model.FavoriteApod
import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesRepository
import org.itis.project.sharedlogic.feature.favorites.api.domain.GetFavoritesUseCase


class GetFavoritesUseCaseImpl(
    private val favoritesRepository: FavoritesRepository
) : GetFavoritesUseCase {
    override suspend operator fun invoke(): List<FavoriteApod> {
        return favoritesRepository.getAllFavorites()
    }
}