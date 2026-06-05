package org.itis.project.sharedui.features.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.itis.project.sharedlogic.feature.main.impl.presentation.HomeEvent
import org.itis.project.sharedlogic.presentation.home.HomeViewModel
import org.itis.project.sharedui.components.AppButton
import org.itis.project.sharedui.components.AppCard
import org.itis.project.sharedui.components.ButtonVariant
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.theme.SpaceTheme
import org.itis.project.sharedui.common.formatCoord
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {

    val vm: HomeViewModel = koinViewModel()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.onScreenOpen()
        vm.obtainIntent(HomeEvent.Load)
    }

    SpaceTheme {
        GradientBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(Dimens.spacing16),
                verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
            ) {
                Text(
                    text = state.greeting,
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                AppCard(glassEffect = true) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)
                    ) {
                        Text(
                            text = "Картинка дня от NASA",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (state.apodLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("Загрузка...", color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        } else if (state.apod != null) {
                            AsyncImage(
                                model = state.apod!!.url,
                                contentDescription = state.apod!!.title,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                            )
                            Text(
                                text = state.apod!!.title,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = state.apod!!.explanation.take(150) + "...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else if (state.apodError != null) {
                            Text(
                                text = state.apodError!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                AppCard(glassEffect = true) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
                    ) {
                        Text(
                            text = "🌟 Планета дня",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = state.planetName,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = state.planetFact,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                AppCard(glassEffect = true) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(Dimens.spacing8)
                    ) {
                        Text(
                            text = "🛰️ Где сейчас МКС?",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        if (state.issLoading) {
                            Text("Загрузка...", color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else if (state.issPosition != null) {
                            Text(
                                text = "Широта: ${formatCoord(state.issPosition!!.latitude)}°",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Долгота: ${formatCoord(state.issPosition!!.longitude)}°",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        } else if (state.issError != null) {
                            Text(
                                text = state.issError!!,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                AppButton(
                    onClick = { vm.obtainIntent(HomeEvent.Refresh) },
                    text = "Обновить данные",
                    variant = ButtonVariant.Primary,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}