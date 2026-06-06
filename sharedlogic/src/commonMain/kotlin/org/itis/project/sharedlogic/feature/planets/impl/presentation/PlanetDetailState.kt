package org.itis.project.sharedlogic.feature.planets.impl.presentation

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel

data class PlanetDetailState(
    val isLoading: Boolean = true,
    val detail: PlanetDetailModel? = null,
    val error: String? = null,
    val isFavorite: Boolean = false
)