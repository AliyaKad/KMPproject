package org.itis.project.sharedlogic.feature.planets.impl.presentation

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetSummaryModel

data class PlanetsState(
    val isLoading: Boolean = true,
    val planets: List<PlanetSummaryModel> = emptyList(),
    val query: String = "",
    val error: String? = null
) {
    val filteredPlanets: List<PlanetSummaryModel>
        get() = if (query.isBlank()) planets
        else planets.filter { it.name.contains(query, ignoreCase = true) }
}