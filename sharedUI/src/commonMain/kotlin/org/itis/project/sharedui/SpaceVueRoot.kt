package org.itis.project.sharedui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import org.itis.project.sharedui.features.home.HomeScreen
import org.itis.project.sharedui.theme.SpaceTheme

@Composable
fun SpaceVueRoot() {
    setSingletonImageLoaderFactory { context ->
        ImageLoader.Builder(context)
            .components { add(KtorNetworkFetcherFactory()) }
            .crossfade(true)
            .build()
    }

    val isDark = isSystemInDarkTheme()

    SpaceTheme(darkTheme = isDark) {
        HomeScreen()
    }
}