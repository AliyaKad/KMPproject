package org.itis.project.sharedui

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import org.itis.project.sharedui.features.apod.ApodDetailScreen
import org.itis.project.sharedui.features.home.HomeScreen
import org.itis.project.sharedui.features.planets.PlanetDetailScreen
import org.itis.project.sharedui.features.planets.PlanetsScreen

@Composable
fun SpaceVueRoot(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .crossfade(true)
            .build()
    }

    HomeScreen()
    //ApodDetailScreen(date = "2025-06-03", onBack = { /* пока ничего */ })

//    PlanetsScreen(
//        onPlanetClick = { planetId ->
//            // Пока просто выводим в консоль (позже заменим на навигацию)
//            println("Clicked on planet: $planetId")
//        }
//    )

//    PlanetDetailScreen(
//        planetId = "terre",   // id Земли в API
//        onBack = { /* пока ничего */ }
//    )


//    val authViewModel: AuthViewModel = koinViewModel()
//    val profileViewModel: ProfileViewModel = koinViewModel()
//
//    val authState by authViewModel.state.collectAsState()
//    val profileState by profileViewModel.state.collectAsState()
//
//    val showRegister = remember { mutableStateOf(false) }
//
//    when (authState) {
//        is AuthState.Loading -> {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
//            }
//        }
//
//        is AuthState.Unauthorized,
//        is AuthState.Error -> {
//            if (showRegister.value) {
//                RegisterScreen(
//                    onRegisterClick = { email, username, password ->
//                        authViewModel.handleEvent(
//                            AuthEvent.OnRegister(email, username, password)
//                        )
//                    },
//                    onBackToLogin = { showRegister.value = false }
//                )
//            } else {
//                LoginScreen(
//                    onLoginClick = { email, password ->
//                        authViewModel.handleEvent(
//                            AuthEvent.OnLogin(email, password)
//                        )
//                    },
//                    onRegisterClick = { showRegister.value = true }
//                )
//            }
//        }
//
//        is AuthState.Authorized -> {
//            ProfileScreen(
//                state = profileState,
//                isDarkTheme = isDarkTheme,
//                onThemeChange = onThemeChange,
//                onLogoutClick = { authViewModel.handleEvent(AuthEvent.OnLogout) },
//                onEvent = profileViewModel::handleEvent
//            )
//        }
//    }
}