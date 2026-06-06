package org.itis.project.sharedui.features.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.itis.project.sharedlogic.feature.favorites.impl.presentation.FavoritesEvent
import org.itis.project.sharedlogic.feature.favorites.impl.presentation.FavoritesViewModel
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.generated.resources.Res
import org.itis.project.sharedui.generated.resources.*
import org.itis.project.sharedui.theme.Dimens
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreen(
    onApodClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val vm: FavoritesViewModel = koinViewModel()
    val state by vm.state.collectAsState()

    val favoritesTitle = stringResource(Res.string.favorites_title)
    val emptyTitle = stringResource(Res.string.favorites_empty_title)
    val emptyDescription = stringResource(Res.string.favorites_empty_description)
    val retryButton = stringResource(Res.string.favorites_retry)

    LaunchedEffect(Unit) {
        vm.onScreenOpen()
        vm.handleEvent(FavoritesEvent.LoadFavorites)
    }

    GradientBackground {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(Dimens.spacing16)
        ) {
            Text(
                text = favoritesTitle,
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
                            Text(retryButton)
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
                                text = emptyTitle,
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(Dimens.spacing8))
                            Text(
                                text = emptyDescription,
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