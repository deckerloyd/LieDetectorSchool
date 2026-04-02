package com.captbergatron.liedetectfresh.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = InkBlue,
    secondary = FadedInk,
    background = Color(0xFF0D1A26),  // very dark for dark mode
    surface = InkBlue,
    onBackground = GraphPaper,
    onSurface = GraphPaper
)

private val LightColorScheme = lightColorScheme(
    primary = InkBlue,
    secondary = FadedInk,
    background = GraphPaper,
    surface = AgedPaper,
    onBackground = InkLine,
    onSurface = InkLine
)
@Composable
fun LieDetectFreshTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}