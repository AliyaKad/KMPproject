package org.itis.project.sharedui.nav.components

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import org.itis.project.sharedui.nav.HomeRoute
import org.itis.project.sharedui.nav.isTopLevel

@Composable
fun rememberNavController(
    startDestination: NavKey = HomeRoute
): NavBackStack<NavKey> {
    return rememberNavBackStack(
        configuration = RouteConfig,
        startDestination
    )
}

fun NavBackStack<NavKey>.goBack() {
    if (this.size > 1) {
        this.removeAt(this.lastIndex)
    }
}

fun NavBackStack<NavKey>.navigateTo(route: NavKey) {
    if (route.isTopLevel()) {
        this.clear()
    }
    this += route
}