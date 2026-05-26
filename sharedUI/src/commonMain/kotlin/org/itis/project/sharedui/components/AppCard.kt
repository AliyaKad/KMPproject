package org.itis.project.sharedui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.theme.backgroundDark
import org.itis.project.sharedui.theme.glassEffect

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    glassEffect: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val scheme = MaterialTheme.colorScheme
    val isDark = scheme.background == backgroundDark

    val cardModifier = modifier
        .clip(RoundedCornerShape(Dimens.radiusLarge))
        .then(
            if (glassEffect) {
                Modifier.glassEffect(darkTheme = isDark)
            } else {
                Modifier
                    .background(
                        color = scheme.surface,
                        shape = RoundedCornerShape(Dimens.radiusLarge)
                    )
                    .border(
                        width = 1.dp,
                        color = scheme.outline.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(Dimens.radiusLarge)
                    )
            }
        )
        .then(
            if (onClick != null) {
                Modifier.clickable(onClick = onClick)
            } else Modifier
        )
        .padding(Dimens.cardPadding)

    Box(modifier = cardModifier) {
        content()
    }
}