package org.itis.project.sharedui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.itis.project.sharedui.theme.Dimens
import org.itis.project.sharedui.theme.LocalSpaceVisuals

/**
 * Glass-карточка для экранов авторизации.
 * Не зависит от `MaterialTheme.colorScheme.background` (как старый `AppCard`),
 * а корректно подхватывает [LocalSpaceVisuals.isDark].
 */
@Composable
fun AuthGlassCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val isDark = LocalSpaceVisuals.current.isDark
    val shape = RoundedCornerShape(Dimens.radiusLarge)

    val containerColor = MaterialTheme.colorScheme.surface.copy(
        alpha = if (isDark) 0.55f else 0.85f
    )
    val borderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)

    Box(
        modifier = modifier
            .clip(shape)
            .background(color = containerColor, shape = shape)
            .border(width = 1.dp, color = borderColor, shape = shape)
            .padding(Dimens.cardPadding)
    ) {
        content()
    }
}
