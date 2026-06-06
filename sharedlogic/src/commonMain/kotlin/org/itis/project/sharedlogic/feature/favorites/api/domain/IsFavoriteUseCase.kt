package org.itis.project.sharedlogic.feature.favorites.api.domain

interface IsFavoriteUseCase {
    suspend operator fun invoke(date: String): Boolean
}