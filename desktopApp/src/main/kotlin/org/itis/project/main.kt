package org.itis.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.itis.project.sharedui.App


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KMP Project"
    ) {
        App()
    }
}