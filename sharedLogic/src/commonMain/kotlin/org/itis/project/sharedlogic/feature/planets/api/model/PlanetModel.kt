package org.itis.project.sharedlogic.feature.planets.api.model

import kotlinx.serialization.Serializable

@Serializable
data class PlanetModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val moons: Int,
    val gravity: Double?,
    val radiusKm: Double?
)

@Serializable
data class PlanetDetailModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val englishName: String,
    val massKg: Double?,
    val volumeKm3: Double?,
    val gravity: Double?,
    val meanRadiusKm: Double?,
    val perihelionKm: Double?,
    val aphelionKm: Double?,
    val avgTemperatureK: Double?,
    val moons: Int,
    val funFact: String
)