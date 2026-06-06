package org.itis.project.sharedui.features.planets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetSummaryModel
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetsIntent
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetsViewModel
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.design.Loading
import org.itis.project.sharedui.design.SearchField
import org.itis.project.sharedui.generated.resources.Res
import org.itis.project.sharedui.generated.resources.*
import org.itis.project.sharedui.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun PlanetsScreen(
    onPlanetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: PlanetsViewModel = koinInject()
    val state by vm.state.collectAsState()

    val planetsTitle = stringResource(Res.string.planets_title)
    val searchPlaceholder = stringResource(Res.string.planets_search_placeholder)

    LaunchedEffect(Unit) {
        vm.onScreenOpen()
    }

    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(Dimens.spacing16)
        ) {
            Text(
                text = planetsTitle,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(Dimens.spacing12))

            SearchField(
                value = state.query,
                onValueChange = { vm.obtainIntent(PlanetsIntent.Search(it)) },
                placeholder = searchPlaceholder
            )
            Spacer(modifier = Modifier.height(Dimens.spacing16))

            when {
                state.isLoading -> Loading()
                state.error != null -> Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(Dimens.spacing16)
                )
                else -> LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 300.dp),
                    verticalArrangement = Arrangement.spacedBy(Dimens.spacing16),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.spacing16)
                ) {
                    items(state.filteredPlanets, key = { it.id }) { planet ->
                        PlanetCard(
                            planet = planet,
                            onClick = {
                                onPlanetClick(planet.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanetCard(
    planet: PlanetSummaryModel,
    onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(Dimens.spacing16)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = planet.imageUrl,
                    contentDescription = planet.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(Dimens.spacing12))
            Text(
                text = planet.name,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(Dimens.spacing8))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("📏", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = planet.radiusKm?.let { "${it.toLong()} км" } ?: "—",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("⚡", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = planet.gravity?.let { "${it} м/с²" } ?: "—",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🌑", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = "${planet.moons}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                }
            }
        }
    }
}