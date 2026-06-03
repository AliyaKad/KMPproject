package org.itis.project

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import org.itis.project.sharedlogic.feature.auth.impl.di.createAppModule
import org.itis.project.sharedlogic.core.di.networkModule
import org.itis.project.sharedlogic.feature.featureModule
import org.itis.project.sharedui.App
import org.itis.project.sharedui.di.uiModule
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin

fun main() = application {
    if (GlobalContext.getOrNull() == null) {
        startKoin {
            modules(
                networkModule,
                createAppModule(DesktopPlatformDependencies()),
                featureModule,
                uiModule
            )
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