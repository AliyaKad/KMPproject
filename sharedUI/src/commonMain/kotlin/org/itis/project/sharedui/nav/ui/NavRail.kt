package org.itis.project.sharedui.nav.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import org.itis.project.sharedui.nav.*

@Composable
fun NavRail(
    current: NavKey,
    onSelect: (NavKey) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
        tonalElevation = 8.dp,
        modifier = Modifier.fillMaxHeight().width(96.dp)
    ) {
        Column(
            Modifier.fillMaxHeight().padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("SV", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))
            TopLevelRoutes.forEach { route ->
                NavCell(
                    icon = iconFor(route),
                    label = route.label(),
                    selected = current == route,
                    onClick = { onSelect(route) },
                    vertical = true
                )
            }
        }
    }
}