package org.itis.project.sharedlogic.feature.planets.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PlanetSummaryModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val moons: Int,
    val gravity: Double?,
    val radiusKm: Double?
)