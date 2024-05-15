package com.example.jetpackcomposesetup.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.example.jetpackcomposesetup.ui.AppLanguage

// Note : This for theme in app
/*private val lightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
)
private val darkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
)*/

private val light = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    tertiary = Tertiary
)

private val localCustomTypography = staticCompositionLocalOf { Typography() }


@Composable
fun JetpackComposeSetupTheme(
    language: AppLanguage? = AppLanguage.KHMER,
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Note : Dynamic color is the key part of Material You,
    // in which an algorithm derives custom colors from a user’s wallpaper to be applied to their apps and system UI
    /*val colorScheme = when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }

        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }*/

    // Note : Currently I am using none dynamic theme just focus on light theme
    val colorScheme = light

    val customTypography = customTypography(language ?: AppLanguage.KHMER)

    CompositionLocalProvider(localCustomTypography provides customTypography) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = localCustomTypography.current,
            content = content
        )
    }
}