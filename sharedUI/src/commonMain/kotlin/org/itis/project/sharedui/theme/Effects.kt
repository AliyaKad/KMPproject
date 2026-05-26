package org.itis.project.sharedui.theme

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

fun Modifier.glassEffect(darkTheme: Boolean): Modifier = drawBehind {
    if (darkTheme) {
        drawRect(color = Color(0x26FFFFFF))
        drawRect(
            color = Color(0x33FFFFFF),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
        )
    } else {
        drawRect(color = Color(0x66FFFFFF))
        drawRect(
            color = Color(0xFF9D7CD8).copy(alpha = 0.5f),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.dp.toPx())
        )
    }
}

fun cosmicGradientBrush(darkTheme: Boolean): Brush {
    return if (darkTheme) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF000000),
                Color(0xFF0D0A1A),
                Color(0xFF1A1524)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFFAF7FF),
                Color(0xFFE8E0F5)
            )
        )
    }
}