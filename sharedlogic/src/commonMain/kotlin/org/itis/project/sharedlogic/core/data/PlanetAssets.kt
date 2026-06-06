package org.itis.project.sharedlogic.core.data

import org.itis.project.sharedlogic.feature.planets.api.model.PlanetDetailModel
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetSummaryModel

object PlanetAssets {

    data class Entry(
        val id: String,
        val nameRu: String,
        val nameEn: String,
        val imageUrl: String,
        val funFact: String,
        val moons: Int,
        val massKg: Double,
        val volumeKm3: Double,
        val gravity: Double,
        val meanRadiusKm: Double,
        val perihelionKm: Double,
        val aphelionKm: Double,
        val avgTemperatureK: Double
    )

    private val planets: List<Entry> = listOf(
        Entry(
            id = "mercure",
            nameRu = "Меркурий",
            nameEn = "Mercury",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/4/4a/Mercury_in_true_color.jpg",            funFact = "Сутки на Меркурии длятся почти 59 земных суток.",
            moons = 0,
            massKg = 3.3011e23, volumeKm3 = 6.083e10,
            gravity = 3.7, meanRadiusKm = 2439.7,
            perihelionKm = 4.6e7, aphelionKm = 6.98e7,
            avgTemperatureK = 440.0
        ),
        Entry(
            id = "venus",
            nameRu = "Венера",
            nameEn = "Venus",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/e/e5/Venus-real_color.jpg",
            funFact = "Венера вращается в обратную сторону: Солнце там встаёт на западе.",
            moons = 0,
            massKg = 4.8675e24, volumeKm3 = 9.2843e11,
            gravity = 8.87, meanRadiusKm = 6051.8,
            perihelionKm = 1.075e8, aphelionKm = 1.089e8,
            avgTemperatureK = 737.0
        ),
        Entry(
            id = "terre",
            nameRu = "Земля",
            nameEn = "Earth",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/97/The_Earth_seen_from_Apollo_17.jpg",
            funFact = "Земля — единственная планета, на которой подтверждена жизнь.",
            moons = 1,
            massKg = 5.972e24, volumeKm3 = 1.08321e12,
            gravity = 9.807, meanRadiusKm = 6371.0,
            perihelionKm = 1.471e8, aphelionKm = 1.521e8,
            avgTemperatureK = 288.0
        ),
        Entry(
            id = "mars",
            nameRu = "Марс",
            nameEn = "Mars",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/0/02/OSIRIS_Mars_true_color.jpg",
            funFact = "На марсе классно! А ещё на Марсе находится Олимп — самый высокий вулкан Солнечной системы (≈22 км).",
            moons = 2,
            massKg = 6.4171e23, volumeKm3 = 1.6318e11,
            gravity = 3.71, meanRadiusKm = 3389.5,
            perihelionKm = 2.066e8, aphelionKm = 2.492e8,
            avgTemperatureK = 210.0
        ),
        Entry(
            id = "jupiter",
            nameRu = "Юпитер",
            nameEn = "Jupiter",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/2/2b/Jupiter_and_its_shrunken_Great_Red_Spot.jpg",
            funFact = "Большое Красное Пятно Юпитера — шторм, который бушует уже более 350 лет.",
            moons = 95,
            massKg = 1.8982e27, volumeKm3 = 1.4313e15,
            gravity = 24.79, meanRadiusKm = 69911.0,
            perihelionKm = 7.4052e8, aphelionKm = 8.1662e8,
            avgTemperatureK = 165.0
        ),
        Entry(
            id = "saturne",
            nameRu = "Сатурн",
            nameEn = "Saturn",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/c/c7/Saturn_during_Equinox.jpg",
            funFact = "Кольца Сатурна почти полностью состоят из водяного льда.",
            moons = 146,
            massKg = 5.6834e26, volumeKm3 = 8.2713e14,
            gravity = 10.44, meanRadiusKm = 58232.0,
            perihelionKm = 1.35255e9, aphelionKm = 1.5145e9,
            avgTemperatureK = 134.0
        ),
        Entry(
            id = "uranus",
            nameRu = "Уран",
            nameEn = "Uranus",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/3/3d/Uranus2.jpg",
            funFact = "Уран вращается, лёжа на боку: ось наклонена на 98°.",
            moons = 27,
            massKg = 8.6810e25, volumeKm3 = 6.833e13,
            gravity = 8.69, meanRadiusKm = 25362.0,
            perihelionKm = 2.7481e9, aphelionKm = 3.0036e9,
            avgTemperatureK = 76.0
        ),
        Entry(
            id = "neptune",
            nameRu = "Нептун",
            nameEn = "Neptune",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/56/Neptune_Full.jpg",
            funFact = "Ветра на Нептуне самые быстрые в Солнечной системе — до 2100 км/ч.",
            moons = 14,
            massKg = 1.02413e26, volumeKm3 = 6.254e13,
            gravity = 11.15, meanRadiusKm = 24622.0,
            perihelionKm = 4.4598e9, aphelionKm = 4.5396e9,
            avgTemperatureK = 72.0
        )
    )

    private val byId: Map<String, Entry> = planets.associateBy { it.id }

    fun all(): List<PlanetSummaryModel> = planets.map { it.toSummary() }

    fun detail(id: String): PlanetDetailModel? = byId[id]?.toDetail()

    fun planetOfTheDay(seed: Long): Pair<String, String> {
        val idx = ((seed % planets.size) + planets.size) % planets.size
        val p = planets[idx.toInt()]
        return p.nameRu to p.funFact
    }

    private fun Entry.toSummary() = PlanetSummaryModel(
        id = id,
        name = nameRu,
        imageUrl = imageUrl,
        moons = moons,
        gravity = gravity,
        radiusKm = meanRadiusKm
    )

    private fun Entry.toDetail() = PlanetDetailModel(
        id = id,
        name = nameRu,
        imageUrl = imageUrl,
        englishName = nameEn,
        massKg = massKg,
        volumeKm3 = volumeKm3,
        gravity = gravity,
        meanRadiusKm = meanRadiusKm,
        perihelionKm = perihelionKm,
        aphelionKm = aphelionKm,
        avgTemperatureK = avgTemperatureK,
        moons = moons,
        funFact = funFact
    )
}
