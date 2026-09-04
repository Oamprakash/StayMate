package com.staymate.booking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Teal = Color(0xFF1F6F5C)
private val TealDark = Color(0xFF10513F)
private val Sand = Color(0xFFF6F4EF)
private val Amber = Color(0xFFE0A106)

private val LightColors = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFCFEAE0),
    onPrimaryContainer = TealDark,
    secondary = Amber,
    onSecondary = Color.White,
    background = Sand,
    onBackground = Color(0xFF1B1C1B),
    surface = Color.White,
    onSurface = Color(0xFF1B1C1B),
    surfaceVariant = Color(0xFFE8EDE9),
    onSurfaceVariant = Color(0xFF44514B)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF7FD6BC),
    onPrimary = Color(0xFF00382A),
    secondary = Amber,
    background = Color(0xFF12140F),
    surface = Color(0xFF1B1E1A)
)

@Composable
fun StayMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
