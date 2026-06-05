package org.itis.project.sharedui.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.itis.project.domain.User
import org.itis.project.sharedlogic.feature.profile.impl.presentation.ProfileEvent
import org.itis.project.sharedlogic.feature.profile.impl.presentation.ProfileState
import org.itis.project.sharedui.components.AppButton
import org.itis.project.sharedui.components.AppCard
import org.itis.project.sharedui.components.ButtonVariant
import org.itis.project.sharedui.components.GradientBackground
import org.itis.project.sharedui.theme.Dimens

@Composable
fun ProfileScreen(
    state: ProfileState,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onLogoutClick: () -> Unit,
    onEvent: (ProfileEvent) -> Unit
) {
    LaunchedEffect(Unit) {
        onEvent(ProfileEvent.LoadProfile)
    }

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        state.errorMessage != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = state.errorMessage!!,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        else -> {
            ProfileContent(
                user = state.user,
                isDarkTheme = isDarkTheme,
                onThemeChange = { value ->
                    onThemeChange(value)
                    onEvent(ProfileEvent.UpdateTheme(value))
                },
                onLogoutClick = onLogoutClick
            )
        }
    }
}

@Composable
fun ProfileContent(
    user: User?,
    isDarkTheme: Boolean,
    onThemeChange: (Boolean) -> Unit,
    onLogoutClick: () -> Unit
) {
    GradientBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(Dimens.spacing16),
            verticalArrangement = Arrangement.spacedBy(Dimens.spacing16)
        ) {
            Text(
                text = "Профиль",
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            AppCard(glassEffect = true, modifier = Modifier.fillMaxWidth()) {
                Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12)
                    ) {
                        Box(
                            modifier = Modifier
                                .height(56.dp)
                                .width(56.dp)
                                .background(
                                    color = getUserAvatarColor(user),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user?.username?.take(2)?.uppercase() ?: "?",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Column {
                            Text(
                                text = user?.username ?: "Гость",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = user?.email ?: "Не авторизован",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimens.spacing4))

                    Text(
                        text = "ID аккаунта: ${user?.id ?: "—"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            AppCard(glassEffect = true, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Тема приложения",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isDarkTheme) "Темная" else "Светлая",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = onThemeChange
                    )
                }
            }

            AppButton(
                onClick = onLogoutClick,
                text = "Выйти из аккаунта",
                variant = ButtonVariant.Secondary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun getUserAvatarColor(user: User?): Color {
    val colors = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.secondary,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.error,
        MaterialTheme.colorScheme.primaryContainer,
        MaterialTheme.colorScheme.secondaryContainer,
        MaterialTheme.colorScheme.tertiaryContainer
    )

    val index = (user?.id ?: 0).toInt().mod(colors.size)
    return colors[index]
}

private fun Modifier.width(size: Int): Modifier = this.then(Modifier.size(size.dp))