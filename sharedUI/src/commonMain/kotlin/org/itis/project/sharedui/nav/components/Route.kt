package org.itis.project.sharedui.nav

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable


@Serializable
data object HomeRoute : NavKey {
}

@Serializable
data object ApodRoute : NavKey {
}

@Serializable
data object PlanetsRoute : NavKey {
}

@Serializable
data object IssRoute : NavKey {
}

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
    ApodRoute,
    PlanetsRoute,
    IssRoute,
    FavoritesRoute,
    ProfileRoute
)

fun NavKey.label(): String = when (this) {
    HomeRoute -> "Главная"
    ApodRoute -> "NASA"
    PlanetsRoute -> "Планеты"
    IssRoute -> "МКС"
    FavoritesRoute -> "Избранное"
    ProfileRoute -> "Профиль"
    is PlanetDetailRoute -> "Планета"
    else -> ""
}

fun NavKey.isTopLevel(): Boolean = when (this) {
    HomeRoute, ApodRoute, PlanetsRoute, IssRoute, FavoritesRoute, ProfileRoute -> true
    else -> false
}