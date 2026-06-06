package org.itis.project.sharedlogic.feature.apod.impl.presentation.ApodDetail

import org.itis.project.sharedlogic.feature.main.api.model.ApodModel

data class ApodDetailState(
    val isLoading: Boolean = true,
    val apod: ApodModel? = null,
    val isFavorite: Boolean = false,
    val error: String? = null
)
