package org.itis.project.sharedlogic.feature.favorites.api.domain

import org.itis.project.sharedlogic.core.domain.model.FavoriteApod

interface AddToFavoritesUseCase {
    suspend operator fun invoke(apod: FavoriteApod)
}