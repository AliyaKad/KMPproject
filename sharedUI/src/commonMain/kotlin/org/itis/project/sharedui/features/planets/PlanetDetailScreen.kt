package org.itis.project.sharedui.features.planets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetDetailIntent
import org.itis.project.sharedlogic.feature.planets.impl.presentation.PlanetDetailViewModel
import org.itis.project.sharedui.common.formatBig
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.design.Loading
import org.itis.project.sharedui.generated.resources.Res
import org.itis.project.sharedui.generated.resources.*
import org.itis.project.sharedui.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
fun PlanetDetailScreen(
    planetId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: PlanetDetailViewModel = koinInject()
    val state by vm.state.collectAsState()

    val backText = stringResource(Res.string.planet_detail_back)
    val favoriteText = stringResource(Res.string.planet_detail_favorite)
    val gravity = stringResource(Res.string.planet_detail_gravity)
    val radius = stringResource(Res.string.planet_detail_radius)
    val mass = stringResource(Res.string.planet_detail_mass)
    val volume = stringResource(Res.string.planet_detail_volume)
    val temperature = stringResource(Res.string.planet_detail_temperature)
    val perihelion = stringResource(Res.string.planet_detail_perihelion)
    val aphelion = stringResource(Res.string.planet_detail_aphelion)
    val moons = stringResource(Res.string.planet_detail_moons)
    val interestingFact = stringResource(Res.string.planet_detail_interesting_fact)

    LaunchedEffect(Unit) {
        vm.onScreenOpen(planetId)
        vm.obtainIntent(PlanetDetailIntent.Load(planetId))
    }

    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(Dimens.spacing16)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = backText,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Spacer(modifier = Modifier.width(Dimens.spacing8))
                Text(
                    text = state.detail?.name ?: "…",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(Dimens.spacing12))

            when {
                state.isLoading -> Loading()
                state.error != null -> Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error
                )
                state.detail != null -> {
                    val planet = state.detail!!
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f)
                        )
                    ) {
                        Column {
                            AsyncImage(
                                model = planet.imageUrl,
                                contentDescription = planet.name,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                                    .clip(MaterialTheme.shapes.medium)
                            )
                            Column(
                                modifier = Modifier.padding(Dimens.spacing16),
                                verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
                            ) {
                                Text(
                                    text = planet.englishName,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                StatRow(gravity, planet.gravity?.let { "${it} м/с²" } ?: "—")
                                StatRow(radius, planet.meanRadiusKm?.let { "${it.toLong()} км" } ?: "—")
                                StatRow(mass, planet.massKg?.let { "${formatBig(it)} кг" } ?: "—")
                                StatRow(volume, planet.volumeKm3?.let { "${formatBig(it)} км³" } ?: "—")
                                StatRow(temperature, planet.avgTemperatureK?.let { "${it.toInt()} K" } ?: "—")
                                StatRow(perihelion, planet.perihelionKm?.let { "${formatBig(it)} км" } ?: "—")
                                StatRow(aphelion, planet.aphelionKm?.let { "${formatBig(it)} км" } ?: "—")
                                StatRow(moons, planet.moons.toString())
                                Spacer(modifier = Modifier.height(Dimens.spacing8))
                                Text(
                                    text = interestingFact,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = planet.funFact,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}