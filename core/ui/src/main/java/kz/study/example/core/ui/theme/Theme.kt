package kz.study.example.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val AppColorScheme = darkColorScheme(
    primary = Accent,
    onPrimary = OnBackground,
    primaryContainer = ActiveIndicator,
    onPrimaryContainer = Accent,
    secondary = LoginGreen,
    onSecondary = OnBackground,
    secondaryContainer = InputBackground,
    onSecondaryContainer = OnBackground,
    tertiary = RatingColor,
    onTertiary = OnBackground,
    background = Background,
    onBackground = OnBackground,
    surface = CardBackground,
    onSurface = OnBackground,
    surfaceVariant = InputBackground,
    onSurfaceVariant = TextSecondary,
    outline = LoginDivider,
    outlineVariant = DividerColor
)

@Composable
fun ThousandCoursesTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorScheme,
        typography = Typography,
        content = content
    )
}
