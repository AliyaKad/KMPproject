package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail

sealed class ApodDetailEffect {
    data class FavoriteToggled(val isFavorite: Boolean) : ApodDetailEffect()
    data class ShareRequested(val url: String) : ApodDetailEffect()
}