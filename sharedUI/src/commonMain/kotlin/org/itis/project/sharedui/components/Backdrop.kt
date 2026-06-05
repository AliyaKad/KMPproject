package org.itis.project.sharedui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import org.itis.project.sharedui.theme.BackdropColor
import org.itis.project.sharedui.theme.LocalSpaceVisuals
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun StarryBackdrop(content: @Composable () -> Unit) {
    val isDark = LocalSpaceVisuals.current.isDark
    val brush = if (isDark) BackdropColor.nebulaGradient() else BackdropColor.lightSkyGradient()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    ) {
        if (isDark) StarsLayer()
        content()
    }
}

@Composable
private fun StarsLayer() {
    val stars = remember {
        val rnd = Random(42)
        List(96) {
            Triple(rnd.nextFloat(), rnd.nextFloat(), 0.3f + rnd.nextFloat() * 1.8f)
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        val w = size.width; val h = size.height
        stars.forEach { (xn, yn, radius) ->
            drawCircle(
                color = Color.White.copy(alpha = (radius / 2.5f).coerceIn(0.2f, 0.9f)),
                radius = radius,
                center = Offset(xn * w, yn * h)
            )
        }
        val nebulaCenter = Offset(w * 0.72f, h * 0.18f)
        for (i in 0 until 14) {
            val a = i / 14.0 * 2.0 * PI
            drawCircle(
                color = BackdropColor.Aurora.copy(alpha = 0.04f),
                radius = (40 + i * 6).toFloat(),
                center = Offset(
                    (nebulaCenter.x + cos(a).toFloat() * 18f),
                    (nebulaCenter.y + sin(a).toFloat() * 12f)
                )
            )
        }
    }
}
