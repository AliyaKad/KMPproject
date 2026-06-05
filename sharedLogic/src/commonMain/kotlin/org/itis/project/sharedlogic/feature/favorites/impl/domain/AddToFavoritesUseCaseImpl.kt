package org.itis.project.sharedlogic.feature.favorites.impl.domain

import org.itis.project.sharedlogic.core.domain.model.FavoriteApod
import org.itis.project.sharedlogic.feature.favorites.api.data.FavoritesRepository
import org.itis.project.sharedlogic.feature.favorites.api.domain.AddToFavoritesUseCase


class AddToFavoritesUseCaseImpl(
    private val favoritesRepository: FavoritesRepository
) : AddToFavoritesUseCase {
    override suspend operator fun invoke(apod: FavoriteApod) {
        favoritesRepository.addToFavorites(apod)
    }
}