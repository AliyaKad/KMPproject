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
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.theme.SpaceTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlanetDetailScreen(
    planetId: String,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: PlanetDetailViewModel = koinViewModel()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.obtainIntent(PlanetDetailIntent.Load(planetId))
    }

    SpaceTheme {
        GradientBackground {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(Dimens.spacing16)
            ) {
                // Заголовок с кнопкой назад и избранным
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад",
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
                    IconButton(onClick = { vm.obtainIntent(PlanetDetailIntent.ToggleFavorite) }) {
                        Icon(
                            if (state.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "В избранное",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
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
                        // Карточка с контентом
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
                                    StatRow("Гравитация", planet.gravity?.let { "${it} м/с²" } ?: "—")
                                    StatRow("Средний радиус", planet.meanRadiusKm?.let { "${it.toLong()} км" } ?: "—")
                                    StatRow("Масса", planet.massKg?.let { "${formatBig(it)} кг" } ?: "—")
                                    StatRow("Объём", planet.volumeKm3?.let { "${formatBig(it)} км³" } ?: "—")
                                    StatRow("Температура", planet.avgTemperatureK?.let { "${it.toInt()} K" } ?: "—")
                                    StatRow("Перигелий", planet.perihelionKm?.let { "${formatBig(it)} км" } ?: "—")
                                    StatRow("Афелий", planet.aphelionKm?.let { "${formatBig(it)} км" } ?: "—")
                                    StatRow("Спутники", planet.moons.toString())
                                    Spacer(modifier = Modifier.height(Dimens.spacing8))
                                    Text(
                                        text = "Интересный факт",
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