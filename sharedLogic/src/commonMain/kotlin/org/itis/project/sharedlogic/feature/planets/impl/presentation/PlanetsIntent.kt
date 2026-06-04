package org.itis.project.sharedlogic.feature.planets.impl.presentation

sealed class PlanetsIntent {
    data object Load : PlanetsIntent()
    data class Search(val query: String) : PlanetsIntent()
}