package org.itis.project.sharedui.features.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.itis.project.sharedlogic.feature.favorites.impl.presentation.FavoritesEvent
import org.itis.project.sharedlogic.feature.favorites.impl.presentation.FavoritesViewModel
import org.itis.project.sharedui.components.AppCard
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.theme.SpaceTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreen(
    onApodClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: FavoritesViewModel = koinViewModel()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.onScreenOpen()
        vm.handleEvent(FavoritesEvent.LoadFavorites)
    }

    SpaceTheme {
        GradientBackground {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(Dimens.spacing16)
            ) {
                Text(
                    text = "Избранное",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(Dimens.spacing16))

                when {
                    state.isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    state.error != null -> {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = state.error!!,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(Dimens.spacing16))
                            Button(
                                onClick = {
                                    vm.handleEvent(FavoritesEvent.ClearError)
                                    vm.handleEvent(FavoritesEvent.LoadFavorites)
                                }
                            ) {
                                Text("Повторить")
                            }
                        }
                    }

                    state.favorites.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "✨ Нет избранного",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(Dimens.spacing8))
                                Text(
                                    text = "Добавляйте понравившиеся фото NASA в избранное",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    else -> {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)
                        ) {
                            items(state.favorites, key = { it.date }) { favorite ->
                                FavoriteListItem(
                                    favorite = favorite,
                                    onRemove = {
                                        vm.handleEvent(FavoritesEvent.RemoveFavorite(favorite.date))
                                    },
                                    onClick = {
                                        onApodClick(favorite.date)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}