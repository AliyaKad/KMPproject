package org.itis.project.sharedui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.itis.project.sharedlogic.feature.profile.impl.data.ThemeManager
import org.itis.project.sharedui.theme.SpaceTheme
import org.koin.compose.koinInject

@Composable
fun App() {
    val themeManager: ThemeManager = koinInject()
    val isDarkTheme by themeManager.isDarkTheme.collectAsState()

    SpaceTheme(darkTheme = isDarkTheme) {
        SpaceRoot(
            isDarkTheme = isDarkTheme,
            onThemeChange = { themeManager.setDarkTheme(it) }
        )
    }
}