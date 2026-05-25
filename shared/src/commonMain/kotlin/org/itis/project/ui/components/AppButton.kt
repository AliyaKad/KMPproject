package org.itis.project.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.itis.project.ui.theme.Dimens
import org.itis.project.ui.theme.backgroundDark
import org.itis.project.ui.theme.glassEffect

enum class ButtonVariant {
    Primary, Secondary, Glass, Ghost
}

@Composable
fun AppButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.Primary,
    enabled: Boolean = true
) {
    val scheme = MaterialTheme.colorScheme
    val isDark = scheme.background == backgroundDark

    val backgroundColor = when (variant) {
        ButtonVariant.Primary -> scheme.primary
        ButtonVariant.Secondary -> scheme.surface
        ButtonVariant.Glass -> if (isDark) Color(0x26FFFFFF) else Color(0x66FFFFFF)
        ButtonVariant.Ghost -> Color.Transparent
    }

    val contentColor = when (variant) {
        ButtonVariant.Primary -> scheme.onPrimary
        ButtonVariant.Secondary -> scheme.onSurface
        ButtonVariant.Glass -> scheme.primary
        ButtonVariant.Ghost -> scheme.primary
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.radiusMedium))
            .then(
                if (variant == ButtonVariant.Glass) {
                    Modifier.glassEffect(darkTheme = isDark)
                } else {
                    Modifier.background(
                        color = if (enabled) backgroundColor else backgroundColor.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(Dimens.radiusMedium)
                    )
                }
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(horizontal = Dimens.spacing20, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(Dimens.buttonHeight)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor
            )
        }
    }
}