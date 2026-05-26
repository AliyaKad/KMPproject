package org.itis.project.sharedui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val LocalSpaceColors = staticCompositionLocalOf { SpaceColors() }

data class SpaceColors(
    val glassSurface: Color = Color(0x26FFFFFF),
    val glassBorder: Color = Color(0x33FFFFFF),
    val nebulaPurple: Color = Color(0xFF7B5EA7),
    val nebulaBlue: Color = Color(0xFF4A90E2),
    val accentGlow: Color = Color(0x40C9B6F5),
    val starColor: Color = Color(0xFFFFFFFF)
)

@Composable
fun SpaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = primaryDark,
            onPrimary = onPrimaryDark,
            primaryContainer = primaryContainerDark,
            onPrimaryContainer = onPrimaryContainerDark,
            secondary = secondaryDark,
            onSecondary = onSecondaryDark,
            background = backgroundDark,
            onBackground = onBackgroundDark,
            surface = surfaceDark,
            onSurface = onSurfaceDark,
            error = errorDark,
            onError = onErrorDark,
            outline = Color(0xFF757575).copy(alpha = 0.5f)
        )
    } else {
        lightColorScheme(
            primary = primaryLight,
            onPrimary = onPrimaryLight,
            primaryContainer = primaryContainerLight,
            onPrimaryContainer = onPrimaryContainerLight,
            secondary = secondaryLight,
            onSecondary = onSecondaryLight,
            background = backgroundLight,
            onBackground = onBackgroundLight,
            surface = surfaceLight,
            onSurface = onSurfaceLight,
            error = errorLight,
            onError = onErrorLight,
            outline = Color(0xFF757575).copy(alpha = 0.5f)
        )
    }

    val spaceColors = SpaceColors(
        glassSurface = if (darkTheme) glassSurfaceDark else glassSurfaceLight,
        glassBorder = if (darkTheme) glassBorderDark else glassBorderLight,
        nebulaPurple = nebulaPurple,
        nebulaBlue = nebulaBlue,
        accentGlow = accentGlow,
        starColor = starColor
    )

    CompositionLocalProvider(LocalSpaceColors provides spaceColors) {
        MaterialTheme(
            colorScheme = colors,
            typography = SpaceTypography,
            content = content
        )
    }
}

val MaterialTheme.spaceColors: SpaceColors
    @Composable get() = LocalSpaceColors.current