package org.itis.project.sharedui.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
data object HomeRoute : NavKey {
}

@Serializable
data object ApodSearchRoute : NavKey {
}

@Serializable
data object PlanetsRoute : NavKey {
}

@Serializable
class ApodDetailRoute(val date: String) : NavKey

@Serializable
data object FavoritesRoute : NavKey {
}

@Serializable
data object ProfileRoute : NavKey {
}

@Serializable
data class PlanetDetailRoute(val id: String) : NavKey {
}

val TopLevelRoutes = listOf(
    HomeRoute,
    ApodSearchRoute,
    PlanetsRoute,
    FavoritesRoute,
    ProfileRoute
)

fun NavKey.label(): String = when (this) {
    HomeRoute -> "Главная"
    ApodSearchRoute -> "NASA"
    PlanetsRoute -> "Планеты"
    ApodDetailRoute -> "DAY NASA"
    FavoritesRoute -> "Избранное"
    ProfileRoute -> "Профиль"
    is PlanetDetailRoute -> "Планета"
    else -> ""
}

fun NavKey.isTopLevel(): Boolean = when (this) {
    HomeRoute, ApodSearchRoute, PlanetsRoute, FavoritesRoute, ProfileRoute -> true
    else -> false
}