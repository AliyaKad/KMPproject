package org.itis.project.desktop

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.itis.project.sharedlogic.di.logicModule
import org.itis.project.sharedui.App
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun main() = application {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            modules(logicModule)
        }
    }

    val windowState = rememberWindowState(size = DpSize(1100.dp, 800.dp))
    Window(
        onCloseRequest = ::exitApplication,
        title = "SpaceVue",
        state = windowState
    ) {
        App()
    }
}