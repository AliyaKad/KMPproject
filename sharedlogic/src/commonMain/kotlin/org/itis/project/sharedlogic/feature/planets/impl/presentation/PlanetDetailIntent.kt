package org.itis.project.sharedlogic.feature.planets.impl.presentation

sealed class PlanetDetailIntent {
    data class Load(val id: String) : PlanetDetailIntent()
    data object ToggleFavorite : PlanetDetailIntent()
}
