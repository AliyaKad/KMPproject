package org.itis.project.sharedui.nav.components

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import org.itis.project.sharedui.features.apod.ApodDetailScreen
import org.itis.project.sharedui.features.apod.ApodSearchScreen
import org.itis.project.sharedui.features.home.HomeScreen
import org.itis.project.sharedui.features.planets.PlanetDetailScreen
import org.itis.project.sharedui.features.planets.PlanetsScreen
import org.itis.project.sharedui.nav.ApodDetailRoute
import org.itis.project.sharedui.nav.ApodSearchRoute
import org.itis.project.sharedui.nav.FavoritesRoute
import org.itis.project.sharedui.nav.HomeRoute
import org.itis.project.sharedui.nav.PlanetDetailRoute
import org.itis.project.sharedui.nav.PlanetsRoute
import org.itis.project.sharedui.nav.ProfileRoute
import kotlin.collections.plusAssign
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
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
                        onNavigateToApodDetail = {
                            navController += ApodDetailRoute(date = "")
                        },
                        onNavigateToPlanetDetail = { planetId ->
                            navController += PlanetDetailRoute(planetId)
                        }
                    )
                }

                is ApodDetailRoute -> NavEntry(key) {
                    ApodDetailScreen(
                        date = key.date,
                        onBack = {
                            navController.goBack()
                        }
                    )
                }
                is ApodSearchRoute -> NavEntry(key) {
                    ApodSearchScreen(
                        onBack = {
                            navController.goBack()
                        },
                        onNavigateToDetail = { date ->
                            navController += ApodDetailRoute(date)
                        }
                    )
                }

                is PlanetsRoute -> NavEntry(key) {
                    PlanetsScreen(
                        onPlanetClick = { planetId ->
                            navController += PlanetDetailRoute(planetId)
                        }
                    )
                }


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