package org.itis.project.sharedui.nav

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import org.itis.project.sharedui.features.apod.ApodDetailScreen
import org.itis.project.sharedui.features.home.HomeScreen
import org.itis.project.sharedui.features.planets.PlanetDetailScreen
import org.itis.project.sharedui.features.planets.PlanetsScreen
import org.itis.project.sharedui.nav.components.RouteConfig
import org.itis.project.sharedui.nav.components.goBack

@Composable
fun NavHost(
    navController: NavBackStack<NavKey>
) {

    NavDisplay(
        backStack = navController,

        entryProvider = { key ->

            when (key) {

                is HomeRoute -> NavEntry(key) {
                    HomeScreen(
                        onNavigateToApod = {
                            navController += ApodRoute
                        },
                        onNavigateToPlanetDetail = { planetId ->
                            navController += PlanetDetailRoute(planetId)
                        }
                    )
                }

                is ApodRoute -> NavEntry(key) {
                    ApodDetailScreen(
                        date = "2025-06-03",
                        onBack = {  navController.goBack() }
                    )
                }

                is PlanetsRoute -> NavEntry(key) {
                    PlanetsScreen(
                        onPlanetClick = { planetId ->
                            navController += PlanetDetailRoute(planetId)
                        }
                    )
                }


                is IssRoute -> NavEntry(key) {}

                is FavoritesRoute -> NavEntry(key) {}

                is ProfileRoute -> NavEntry(key) {}

                is PlanetDetailRoute -> NavEntry(key) {
                    PlanetDetailScreen(
                        planetId = key.id,
                        onBack = { navController.goBack() }
                    )
                }

                else -> error("Unknown route: $key")
            }
        }
    )
}