package org.itis.project.sharedui.nav.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import org.itis.project.sharedui.nav.*

@Composable
fun BottomBar(
    current: NavKey,
    onSelect: (NavKey) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.85f),
        tonalElevation = 6.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopLevelRoutes.forEach { route ->
                NavCell(
                    icon = iconFor(route),
                    label = route.label(),
                    selected = current == route,
                    onClick = { onSelect(route) }
                )
            }
        }
    }
}