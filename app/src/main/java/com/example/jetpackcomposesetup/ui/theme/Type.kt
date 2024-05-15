package com.example.jetpackcomposesetup.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.jetpackcomposesetup.ui.AppLanguage
import com.example.jetpackcomposesetup.R


val englishFont by lazy {
    FontFamily(
        Font(R.font.ubuntu, FontWeight.Normal),
    )
}

val khmerFont by lazy {
    FontFamily(
        Font(R.font.battambang_regular, FontWeight.Normal),
    )
}

val defaultFont by lazy {
    FontFamily.Default
}

private val defaultTypography = Typography()

//Note: this deprecated thing is a joke from Android team, without it, we'll see weird bottom padding :(
private val noFontPadding = PlatformTextStyle(includeFontPadding = false)

@Composable
fun customTypography(language: AppLanguage): Typography {

    // Note: Handle changing font the whole app
    val customFontFamily = when (language) {
        AppLanguage.KHMER -> khmerFont
        else -> englishFont
    }

    return Typography(
        // Body
        bodyLarge = defaultTypography.bodyLarge.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        bodyMedium = defaultTypography.bodyMedium.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        bodySmall = defaultTypography.bodySmall.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),

        // Display
        displayLarge = defaultTypography.displayLarge.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        displayMedium = defaultTypography.displayMedium.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        displaySmall = defaultTypography.displaySmall.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),

        // Headline
        headlineLarge = defaultTypography.headlineLarge.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        headlineMedium = defaultTypography.headlineMedium.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        headlineSmall = defaultTypography.headlineSmall.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),

        // Label
        labelLarge = defaultTypography.labelLarge.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        labelMedium = defaultTypography.labelMedium.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        labelSmall = defaultTypography.labelSmall.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),

        // Title
        titleLarge = defaultTypography.titleLarge.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        titleMedium = defaultTypography.titleMedium.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
        titleSmall = defaultTypography.titleSmall.copy(
            fontFamily = customFontFamily, platformStyle = noFontPadding
        ),
    )
}