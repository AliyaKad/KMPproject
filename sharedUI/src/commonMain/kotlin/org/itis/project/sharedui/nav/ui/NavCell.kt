package org.itis.project.sharedui.nav.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Public
import androidx.compose.material.icons.outlined.Rocket
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import org.itis.project.sharedui.features.apod.ApodDetailScreen
import org.itis.project.sharedui.features.apod.ApodSearchScreen
import org.itis.project.sharedui.nav.*

@Composable
fun NavCell(
    icon: ImageVector,
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    vertical: Boolean = false
) {
    val cs = MaterialTheme.colorScheme
    val color = if (selected) cs.primary else cs.onSurfaceVariant
    val bg = if (selected) cs.primary.copy(alpha = 0.14f) else MaterialTheme.colorScheme.surface.copy(alpha = 0f)
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = bg,
        onClick = onClick,
        modifier = Modifier
            .let { if (vertical) it.padding(horizontal = 4.dp) else it.padding(horizontal = 2.dp) }
    ) {
        Column(
            Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = label, tint = color, modifier = Modifier.size(22.dp))
            Spacer(Modifier.height(4.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = color)
        }
    }
}

fun iconFor(route: NavKey): ImageVector = when (route) {
    HomeRoute -> Icons.Outlined.Home
    ApodSearchRoute -> Icons.Outlined.Star
    PlanetsRoute -> Icons.Outlined.Rocket
    ApodDetailRoute -> Icons.Outlined.Public
    FavoritesRoute -> Icons.Outlined.Favorite
    ProfileRoute -> Icons.Outlined.Person
    is PlanetDetailRoute -> Icons.Outlined.Rocket
    else -> Icons.Outlined.Home
}