package org.itis.project.sharedui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.itis.project.sharedui.components.*
import org.itis.project.sharedui.theme.*

@Composable
fun App() {
    var isDarkTheme by remember { mutableStateOf(true) }

    SpaceTheme(darkTheme = isDarkTheme) {
        GradientBackground {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.spacing16),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = Dimens.spacing24)
                    ) {
                        Text(
                            text = if (isDarkTheme) "Dark" else "☀Light",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.width(Dimens.spacing8))
                        AppButton(
                            onClick = { isDarkTheme = !isDarkTheme },
                            text = "Switch",
                            variant = ButtonVariant.Glass
                        )
                    }

                    Text(
                        text = "SpaceVue",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Text(
                        text = "Professional Design System",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier.padding(top = Dimens.spacing4)
                    )

                    Spacer(modifier = Modifier.height(Dimens.spacing32))

                    AppCard(
                        glassEffect = true,
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(Dimens.spacing12)) {
                            Text(
                                text = "✨ Glassmorphism Card",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Text(
                                text = "Transparent background with blur effect and subtle border. Works on both Android and Desktop.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(Dimens.spacing24))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(Dimens.spacing12),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        AppButton(
                            onClick = { println("Primary clicked") },
                            text = "Primary",
                            variant = ButtonVariant.Primary,
                            modifier = Modifier.weight(1f)
                        )
                        AppButton(
                            onClick = { println("Glass clicked") },
                            text = "Glass",
                            variant = ButtonVariant.Glass,
                            modifier = Modifier.weight(1f)
                        )
                    }

                }
            }
        }
    }
}