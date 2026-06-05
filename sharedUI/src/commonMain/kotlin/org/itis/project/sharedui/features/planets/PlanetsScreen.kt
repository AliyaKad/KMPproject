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
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.itis.project.sharedlogic.feature.planets.api.model.PlanetModel
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetsIntent
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetsViewModel
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.design.Loading
import org.itis.project.sharedui.design.SearchField
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.theme.SpaceTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlanetsScreen(
    onPlanetClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: PlanetsViewModel = koinViewModel()
    val state by vm.state.collectAsState()

    var shouldCrash by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        vm.onScreenOpen()
    }

    LaunchedEffect(shouldCrash) {
        if (shouldCrash) {
            vm.logEvent("test_crash_triggered", mapOf("screen" to "planets_list"))
            throw RuntimeException("Test crash from SpaceVue - Planets Screen")
        }
    }

    SpaceTheme {
        GradientBackground {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(Dimens.spacing16)
            ) {
                Text(
                    text = "Планеты",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(Dimens.spacing12))

                Button(
                    onClick = { shouldCrash = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("TEST CRASH")
                }

                Spacer(modifier = Modifier.height(Dimens.spacing12))

                SearchField(
                    value = state.query,
                    onValueChange = { vm.obtainIntent(PlanetsIntent.Search(it)) },
                    placeholder = "Поиск планет…"
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
                        columns = GridCells.Adaptive(minSize = 240.dp),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing12),
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12)
                    ) {
                        items(state.filteredPlanets, key = { it.id }) { planet ->
                            PlanetCard(
                                planet = planet,
                                onClick = {
                                    vm.logPlanetClick(planet.id, planet.name)
                                    onPlanetClick(planet.id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PlanetCard(
    planet: PlanetModel,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(Dimens.spacing12),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(16.dp))
            ) {
                AsyncImage(
                    model = planet.imageUrl,
                    contentDescription = planet.name,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.width(Dimens.spacing12))
            Column {
                Text(
                    text = planet.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(Dimens.spacing4))
                val info = buildList {
                    planet.radiusKm?.let { add("R ${it.toLong()} км") }
                    planet.gravity?.let { add("g ${it} м/с²") }
                    add("🌑 ${planet.moons}")
                }.joinToString(" · ")
                Text(
                    text = info,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}