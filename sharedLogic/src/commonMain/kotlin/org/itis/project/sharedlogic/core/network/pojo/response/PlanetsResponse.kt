package org.itis.project.sharedlogic.core.network.pojo.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetsResponse(
    @SerialName("bodies")
    val bodies: List<PlanetDetailResponse>?
)

@Serializable
data class PlanetDetailResponse(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("englishName")
    val englishName: String? = null,
    @SerialName("isPlanet")
    val isPlanet: Boolean? = false,
    @SerialName("moons")
    val moons: List<MoonRef>? = null,
    @SerialName("mass")
    val mass: Scalar? = null,
    @SerialName("vol")
    val vol: Scalar? = null,
    @SerialName("gravity")
    val gravity: Double? = null,
    @SerialName("meanRadius")
    val meanRadius: Double? = null,
    @SerialName("perihelion")
    val perihelion: Double? = null,
    @SerialName("aphelion")
    val aphelion: Double? = null,
    @SerialName("avgTemp")
    val avgTemp: Double? = null
)

@Serializable
data class MoonRef(
    @SerialName("moon")
    val moon: String?,
    @SerialName("rel")
    val rel: String?
)

@Serializable
data class Scalar(
    @SerialName("massValue")
    val massValue: Double? = null,
    @SerialName("massExponent")
    val massExponent: Int? = null,
    @SerialName("volValue")
    val volValue: Double? = null,
    @SerialName("volExponent")
    val volExponent: Int? = null
) {
    fun toAbsolute(): Double? {
        val v = massValue ?: volValue ?: return null
        val e = massExponent ?: volExponent ?: return v
        var result = v
        repeat(kotlin.math.abs(e)) {
            result = if (e >= 0) result * 10.0 else result / 10.0
        }
        return result
    }
}