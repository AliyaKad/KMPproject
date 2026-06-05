package org.itis.project.sharedui.nav.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthEvent
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthState
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthViewModel
import org.itis.project.sharedui.design.Loading
import org.itis.project.sharedui.features.apod.ApodDetailScreen
import org.itis.project.sharedui.features.apod.ApodSearchScreen
import org.itis.project.sharedui.features.auth.LoginScreen
import org.itis.project.sharedui.features.auth.RegisterScreen
import org.itis.project.sharedui.features.favorites.FavoritesScreen
import org.itis.project.sharedui.features.home.HomeScreen
import org.itis.project.sharedui.features.planets.PlanetDetailScreen
import org.itis.project.sharedui.features.planets.PlanetsScreen
import org.itis.project.sharedui.nav.ApodDetailRoute
import org.itis.project.sharedui.nav.ApodSearchRoute
import org.itis.project.sharedui.nav.AuthRoute
import org.itis.project.sharedui.nav.FavoritesRoute
import org.itis.project.sharedui.nav.HomeRoute
import org.itis.project.sharedui.nav.PlanetDetailRoute
import org.itis.project.sharedui.nav.PlanetsRoute
import org.itis.project.sharedui.nav.ProfileRoute
import org.itis.project.sharedui.nav.RegisterRoute
import org.koin.compose.viewmodel.koinViewModel
import kotlin.collections.plusAssign
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun NavHost(
    navController: NavBackStack<NavKey>
) {

    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.state.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Authorized -> {
                val currentRoute = navController.lastOrNull()
                if (currentRoute == AuthRoute || currentRoute == RegisterRoute) {
                    navController.clear()
                    navController += HomeRoute
                }
            }
            is AuthState.Unauthorized -> {
                val currentRoute = navController.lastOrNull()
                if (currentRoute != AuthRoute && currentRoute != RegisterRoute) {
                    navController.clear()
                    navController += AuthRoute
                }
            }
            else -> {}
        }
    }

    NavDisplay(
        backStack = navController,

        entryProvider = { key ->

            when (key) {

                is AuthRoute -> NavEntry(key) {
                    authViewModel.onLoginScreenOpen()
                    LoginScreen(
                        onLoginClick = { email, password ->
                            authViewModel.handleEvent(AuthEvent.OnLogin(email, password))
                        },
                        onRegisterClick = {
                            navController += RegisterRoute
                        }
                    )
                }

                 is RegisterRoute -> NavEntry(key) {
                    authViewModel.onRegisterScreenOpen()
                    RegisterScreen(
                        onRegisterClick = { email, username, password ->
                            authViewModel.handleEvent(AuthEvent.OnRegister(email, username, password))
                        },
                        onBackToLogin = { navController.goBack() }
                    )
                }

                is HomeRoute -> NavEntry(key) {
                    when (authState) {
                        is AuthState.Authorized -> {
                            HomeScreen(
                                onNavigateToApodDetail = {
                                    navController += ApodDetailRoute(date = "")
                                },
                                onNavigateToPlanetDetail = { planetId ->
                                    navController += PlanetDetailRoute(planetId)
                                }
                            )
                        }

                        is AuthState.Loading -> {
                            Loading()
                        }
                        else -> {}
                    }
                }

                is ApodSearchRoute -> NavEntry(key) {
                    when (authState) {
                        is AuthState.Authorized -> {
                            ApodSearchScreen(
                                onBack = { navController.goBack() },
                                onNavigateToDetail = { date ->
                                    navController += ApodDetailRoute(date)
                                }
                            )
                        }
                        else -> {}
                    }
                }

                is ApodDetailRoute -> NavEntry(key) {
                    when (authState) {
                        is AuthState.Authorized -> {
                            ApodDetailScreen(
                                date = key.date,
                                onBack = { navController.goBack() }
                            )
                        }
                        else -> {}
                    }
                }

                is PlanetsRoute -> NavEntry(key) {
                    when (authState) {
                        is AuthState.Authorized -> {
                            PlanetsScreen(
                                onPlanetClick = { planetId ->
                                    navController += PlanetDetailRoute(planetId)
                                }
                            )
                        }
                        else -> {}
                    }
                }

                is PlanetDetailRoute -> NavEntry(key) {
                    when (authState) {
                        is AuthState.Authorized -> {
                            PlanetDetailScreen(
                                planetId = key.id,
                                onBack = { navController.goBack() }
                            )
                        }
                        else -> {}
                    }
                }

                is FavoritesRoute -> NavEntry(key) {
                    when (authState) {
                        is AuthState.Authorized -> {
                            FavoritesScreen(
                                onApodClick = { date ->
                                    navController += ApodDetailRoute(date)
                                }
                            )
                        }
                        else -> {}
                    }
                }

                is ProfileRoute -> NavEntry(key) {
                    when (authState) {
                        is AuthState.Authorized -> {
                            // ProfileScreen()
                        }
                        else -> {}
                    }
                }

                else -> error("Unknown route: $key")
            }
        }
    )
}