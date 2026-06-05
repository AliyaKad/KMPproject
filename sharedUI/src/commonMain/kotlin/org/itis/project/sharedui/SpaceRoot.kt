package org.itis.project.sharedui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import org.itis.project.sharedui.components.StarryBackdrop
import org.itis.project.sharedui.nav.HomeRoute
import org.itis.project.sharedui.nav.NavHost
import org.itis.project.sharedui.nav.components.rememberNavController
import org.itis.project.sharedui.nav.ui.BottomBar
import org.itis.project.sharedui.nav.ui.NavRail
import org.itis.project.sharedui.theme.SpaceTheme

@Composable
fun SpaceRoot(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit
) {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .crossfade(true)
            .build()
    }

    val navController = rememberNavController(startDestination = HomeRoute)

    fun onTopLevelRouteSelected(route: NavKey) {
        if (navController.last() == route) return
        navController.clear()
        navController += route
    }

    SpaceTheme(darkTheme = isDarkTheme) {
        StarryBackdrop {
            BoxWithConstraints(Modifier.fillMaxSize()) {
                val isWide = maxWidth >= 720.dp

                if (isWide) {
                    Row(Modifier.fillMaxSize()) {
                        NavRail(
                            current = navController.last(),
                            onSelect = ::onTopLevelRouteSelected
                        )
                        NavHost(navController = navController)
                    }
                } else {
                    Column(Modifier.fillMaxSize()) {
                        Box(Modifier.fillMaxSize().weight(1f)) {
                            NavHost(navController = navController)
                        }
                        BottomBar(
                            current = navController.last(),
                            onSelect = ::onTopLevelRouteSelected
                        )
                    }
                }
            }
        }
    }




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