package org.itis.project.sharedui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthEvent
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthState
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthUiState
import org.itis.project.sharedlogic.feature.auth.impl.presentation.AuthViewModel
import org.itis.project.sharedui.components.StarryBackdrop
import org.itis.project.sharedui.features.auth.LoginScreen
import org.itis.project.sharedui.features.auth.RegisterScreen
import org.itis.project.sharedui.nav.HomeRoute
import org.itis.project.sharedui.nav.components.NavHost
import org.itis.project.sharedui.nav.components.rememberNavController
import org.itis.project.sharedui.nav.ui.BottomBar
import org.itis.project.sharedui.nav.ui.NavRail
import org.koin.compose.viewmodel.koinViewModel

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

    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.state.collectAsState()

    var showRegister by remember { mutableStateOf(false) }

    StarryBackdrop {
        AnimatedContent(
            targetState = authStateBucket(authState, showRegister),
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "auth-gate"
        ) { bucket ->
            when (bucket) {
                AuthBucket.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    }
                }

                AuthBucket.Login -> {
                    LoginScreen(
                        viewModel = authViewModel,
                        onLoginSuccess = {  },
                        onNavigateToRegister = {
                            showRegister = true
                            authViewModel.onRegisterScreenOpen()
                        }
                    )
                }

                AuthBucket.Register -> {
                    RegisterScreen(
                        viewModel = authViewModel,
                        onRegisterSuccess = {  },
                        onNavigateToLogin = {
                            showRegister = false
                            authViewModel.onLoginScreenOpen()
                        }
                    )
                }

                AuthBucket.Authorized -> {
                    AuthorizedShell(
                        isDarkTheme = isDarkTheme,
                        onThemeChange = onThemeChange,
                        onLogout = {
                            authViewModel.handleEvent(AuthEvent.OnLogout)
                            showRegister = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun AuthorizedShell(
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onLogout: () -> Unit
) {
    val navController = rememberNavController(startDestination = HomeRoute)

    fun onTopLevelRouteSelected(route: NavKey) {
        if (navController.last() == route) return
        navController.clear()
        navController += route
    }

    BoxWithConstraints(Modifier.fillMaxSize()) {
        val isWide = maxWidth >= 720.dp

        if (isWide) {
            Row(Modifier.fillMaxSize()) {
                NavRail(
                    current = navController.last(),
                    onSelect = ::onTopLevelRouteSelected
                )
                NavHost(
                    navController = navController,
                    isDarkTheme = isDarkTheme,
                    onThemeChange = onThemeChange,
                    onLogout = onLogout
                )
            }
        } else {
            Column(Modifier.fillMaxSize()) {
                Box(Modifier.fillMaxSize().weight(1f)) {
                    NavHost(
                        navController = navController,
                        isDarkTheme = isDarkTheme,
                        onThemeChange = onThemeChange,
                        onLogout = onLogout
                    )
                }
                BottomBar(
                    current = navController.last(),
                    onSelect = ::onTopLevelRouteSelected
                )
            }
        }
    }
}

private enum class AuthBucket { Loading, Login, Register, Authorized }

private fun authStateBucket(state: AuthState, showRegister: Boolean): AuthBucket = when (state) {
    AuthState.Loading -> AuthBucket.Loading
    is AuthState.Authorized -> AuthBucket.Authorized
    AuthState.Unauthorized, is AuthState.Error ->
        if (showRegister) AuthBucket.Register else AuthBucket.Login
}