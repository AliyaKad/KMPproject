package org.itis.project.sharedui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.itis.project.sharedui.theme.SpaceTheme

@Composable
fun App() {
    var isDarkTheme by remember { mutableStateOf(true) }

    SpaceTheme(darkTheme = isDarkTheme) {
        SpaceVueRoot(
            isDarkTheme = isDarkTheme,
            onThemeChange = { isDarkTheme = it }
        )
    }
}