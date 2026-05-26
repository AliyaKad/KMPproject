package org.itis.project.sharedui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.itis.project.sharedui.theme.backgroundDark
import org.itis.project.sharedui.theme.cosmicGradientBrush

@Composable
fun GradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background == backgroundDark

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(cosmicGradientBrush(darkTheme = isDark))
    ) {
        content()
    }
}