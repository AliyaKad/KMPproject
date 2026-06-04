package org.itis.project.sharedlogic.core.network.mapper

import org.itis.project.sharedlogic.core.network.pojo.response.PlanetDetailResponse
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetModel


fun PlanetDetailResponse.mapToModel(): PlanetModel = PlanetModel(
    id = id ?: "",
    name = name ?: "",
    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Mercury_in_true_color.jpg/600px-Mercury_in_true_color.jpg", // fallback, но лучше потом подставить реальные URL
    moons = moons?.size ?: 0,
    gravity = gravity,
    radiusKm = meanRadius
)

fun PlanetDetailResponse.mapToDetail(): PlanetDetailModel = PlanetDetailModel(
    id = id ?: "",
    name = name ?: "",
    imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Mercury_in_true_color.jpg/600px-Mercury_in_true_color.jpg", // замените на реальную логику получения URL
    englishName = englishName ?: name ?: "",
    massKg = mass?.toAbsolute(),
    volumeKm3 = vol?.toAbsolute(),
    gravity = gravity,
    meanRadiusKm = meanRadius,
    perihelionKm = perihelion,
    aphelionKm = aphelion,
    avgTemperatureK = avgTemp,
    moons = moons?.size ?: 0,
    funFact = generateFunFact(name ?: "")
)

private fun generateFunFact(name: String): String = when (name.lowercase()) {
    "mercure" -> "Сутки на Меркурии длятся почти 59 земных суток."
    "venus" -> "Венера вращается в обратную сторону: Солнце там встаёт на западе."
    "terre" -> "Земля — единственная планета, на которой подтверждена жизнь."
    "mars" -> "На Марсе находится Олимп — самый высокий вулкан (≈22 км)."
    "jupiter" -> "Большое Красное Пятно — шторм, бушующий уже более 350 лет."
    "saturne" -> "Кольца Сатурна почти полностью состоят из водяного льда."
    "uranus" -> "Уран вращается, лёжа на боку: ось наклонена на 98°."
    "neptune" -> "Ветра на Нептуне самые быстрые — до 2100 км/ч."
    else -> "Узнайте больше о космосе вместе с SpaceVue!"
}