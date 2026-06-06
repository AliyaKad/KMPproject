package org.itis.project.sharedui.nav.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import org.itis.project.sharedlogic.feature.profile.impl.presentation.ProfileEvent
import org.itis.project.sharedlogic.feature.profile.impl.presentation.ProfileViewModel
import org.itis.project.sharedui.features.apod.ApodDetailScreen
import org.itis.project.sharedui.features.apod.ApodSearchScreen
import org.itis.project.sharedui.features.favorites.FavoritesScreen
import org.itis.project.sharedui.features.home.HomeScreen
import org.itis.project.sharedui.features.planets.PlanetDetailScreen
import org.itis.project.sharedui.features.planets.PlanetsScreen
import org.itis.project.sharedui.features.profile.ProfileScreen
import org.itis.project.sharedui.nav.ApodDetailRoute
import org.itis.project.sharedui.nav.ApodSearchRoute
import org.itis.project.sharedui.nav.FavoritesRoute
import org.itis.project.sharedui.nav.HomeRoute
import org.itis.project.sharedui.nav.PlanetDetailRoute
import org.itis.project.sharedui.nav.PlanetsRoute
import org.itis.project.sharedui.nav.ProfileRoute
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.plusAssign
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun NavHost(
    navController: NavBackStack<NavKey>,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onLogout: () -> Unit
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

                is ApodSearchRoute -> NavEntry(key) {
                    ApodSearchScreen(
                        onBack = { navController.goBack() },
                        onNavigateToDetail = { date ->
                            navController += ApodDetailRoute(date)
                        }
                    )
                }

                is ApodDetailRoute -> NavEntry(key) {
                    ApodDetailScreen(
                        date = key.date,
                        onBack = { navController.goBack() }
                    )
                }

                is PlanetsRoute -> NavEntry(key) {
                    PlanetsScreen(
                        onPlanetClick = { planetId ->
                            navController += PlanetDetailRoute(planetId)
                        }
                    )
                }

                is PlanetDetailRoute -> NavEntry(key) {
                    PlanetDetailScreen(
                        planetId = key.id,
                        onBack = { navController.goBack() }
                    )
                }

                is FavoritesRoute -> NavEntry(key) {
                    FavoritesScreen(
                        onApodClick = { date ->
                            navController += ApodDetailRoute(date)
                        }
                    )
                }

                is ProfileRoute -> NavEntry(key) {
                    val profileVm: ProfileViewModel = koinViewModel()
                    val profileState by profileVm.state.collectAsState()
                    val themeIsDark by profileVm.isDarkTheme.collectAsState()
                    ProfileScreen(
                        state = profileState,
                        isDarkTheme = themeIsDark,
                        onThemeChange = { profileVm.handleEvent(ProfileEvent.UpdateTheme(it)) },
                        onLogoutClick = onLogout,
                        onEvent = profileVm::handleEvent
                    )
                }

                else -> error("Unknown route: $key")
            }
        }
    )
}