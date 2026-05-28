package org.itis.project.sharedui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import org.itis.project.sharedui.features.auth.LoginScreen
import org.itis.project.sharedui.features.auth.RegisterScreen
import org.itis.project.sharedui.features.auth.AuthEvent
import org.itis.project.sharedui.features.auth.AuthState
import org.itis.project.sharedui.features.auth.AuthViewModel
import org.itis.project.sharedui.features.home.HomeScreen
import org.itis.project.sharedui.theme.SpaceTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SpaceVueRoot() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .crossfade(true)
            .build()
    }

    val isDark = isSystemInDarkTheme()
    val authViewModel: AuthViewModel = koinViewModel()
    val authState by authViewModel.state.collectAsState()
    val showRegister = remember { mutableStateOf(false) }

    SpaceTheme(darkTheme = isDark) {
        when (authState) {
            is AuthState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }

            is AuthState.Unauthorized,
            is AuthState.Error -> {
                if (showRegister.value) {
                    RegisterScreen(
                        onRegisterClick = { email, username, password ->
                            authViewModel.handleEvent(
                                AuthEvent.OnRegister(email, username, password)
                            )
                        },
                        onBackToLogin = { showRegister.value = false }
                    )
                } else {
                    LoginScreen(
                        onLoginClick = { email, password ->
                            authViewModel.handleEvent(
                                AuthEvent.OnLogin(email, password)
                            )
                        },
                        onRegisterClick = { showRegister.value = true }
                    )
                }
            }

            is AuthState.Authorized -> {
                HomeScreen()
            }
        }
    }
}
