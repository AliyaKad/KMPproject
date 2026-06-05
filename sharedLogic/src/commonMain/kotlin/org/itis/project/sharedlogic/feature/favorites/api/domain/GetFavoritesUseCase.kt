package org.itis.project.sharedlogic.feature.favorites.api.domain

import org.itis.project.sharedlogic.core.domain.model.FavoriteApod

interface GetFavoritesUseCase {
    suspend operator fun invoke(): List<FavoriteApod>
}